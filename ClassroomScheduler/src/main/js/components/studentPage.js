'use strict';
import React from 'react';
import ReactDOM from 'react-dom';
const when = require('when');
const client = require('./common/client');

const follow = require('./common/follow'); // function to hop multiple links by "rel"
const stompClient = require('./common/websocket-listener');
const root = '/api';

import StudentList from './studentList';
import CreateDialog from './createDialog';

class StudentPage extends React.Component {
	constructor(props) {
		super(props);
		this.state = {students: [], attributes: [], page: 1, pageSize: 4, links: {}};
		this.updatePageSize = this.updatePageSize.bind(this);
		this.onCreate = this.onCreate.bind(this);
		this.onUpdate = this.onUpdate.bind(this);
		this.onDelete = this.onDelete.bind(this);
		this.onNavigate = this.onNavigate.bind(this);
		this.refreshCurrentPage = this.refreshCurrentPage.bind(this);
		this.refreshAndGoToLastPage = this.refreshAndGoToLastPage.bind(this);
	}
	
	loadFromServer(pageSize) {
		follow(client, root, [
				{rel: 'students', params: {size: pageSize}}]
		).then(studentCollection => {
			return client({
				method: 'GET',
				path: studentCollection.entity._links.profile.href,
				headers: {'Accept': 'application/schema+json'}
			}).then(schema => {
				// tag::json-schema-filter[]
				/**
				 * Filter unneeded JSON Schema properties, like uri references and
				 * subtypes ($ref).
				 */
				Object.keys(schema.entity.properties).forEach(function (property) {
					if (schema.entity.properties[property].hasOwnProperty('format') &&
						schema.entity.properties[property].format === 'uri') {
						delete schema.entity.properties[property];
					}
					else if (schema.entity.properties[property].hasOwnProperty('$ref')) {
						delete schema.entity.properties[property];
					}
				});

				this.schema = schema.entity;
				this.links = studentCollection.entity._links;
				return studentCollection;
				// end::json-schema-filter[]
			});
		}).then(studentCollection => {
			this.page = studentCollection.entity.page;
			return studentCollection.entity._embedded.students.map(student =>
					client({
						method: 'GET',
						path: student._links.self.href
					})
			);
		}).then(studentPromises => {
			return when.all(studentPromises);
		}).done(students => {
			this.setState({
				page: this.page,
				students: students,
				attributes: Object.keys(this.schema.properties),
				pageSize: pageSize,
				links: this.links
			});
		});
	}
	// tag::on-create[]
	onCreate(newStudent) {
		follow(client, root, ['students']).done(response => {
			client({
				method: 'POST',
				path: response.entity._links.self.href,
				entity: newStudent,
				headers: {'Content-Type': 'application/json'}
			})
		})
	}
	// end::on-create[]

	// tag::on-update[]
	onUpdate(student, updatedStudent) {
		client({
			method: 'PUT',
			path: student.entity._links.self.href,
			entity: updatedStudent,
			headers: {
				'Content-Type': 'application/json',
				'If-Match': student.headers.Etag
			}
		}).done(response => {
			/* Let the websocket handler update the state */
		}, response => {
			if (response.status.code === 403) {
				alert('ACCESS DENIED: You are not authorized to update ' +
					student.entity._links.self.href);
			}
			if (response.status.code === 412) {
				alert('DENIED: Unable to update ' + student.entity._links.self.href +
					'. Your copy is stale.');
			}
		});
	}
	// end::on-update[]

	// tag::on-delete[]
	onDelete(student) {
		client({method: 'DELETE', path: student.entity._links.self.href}
		).done(response => {/* let the websocket handle updating the UI */},
		response => {
			if (response.status.code === 403) {
				alert('ACCESS DENIED: You are not authorized to delete ' +
					student.entity._links.self.href);
			}
		});
	}
	// end::on-delete[]

	onNavigate(navUri) {
		client({
			method: 'GET',
			path: navUri
		}).then(studentCollection => {
			this.links = studentCollection.entity._links;
			this.page = studentCollection.entity.page;

			return studentCollection.entity._embedded.students.map(student =>
					client({
						method: 'GET',
						path: student._links.self.href
					})
			);
		}).then(studentPromises => {
			return when.all(studentPromises);
		}).done(students => {
			this.setState({
				page: this.page,
				students: students,
				attributes: Object.keys(this.schema.properties),
				pageSize: this.state.pageSize,
				links: this.links
			});
		});
	}


	// tag::websocket-handlers[]
	refreshAndGoToLastPage(message) {
		follow(client, root, [{
			rel: 'students',
			params: {size: this.state.pageSize}
		}]).done(response => {
			if (response.entity._links.last !== undefined) {
				this.onNavigate(response.entity._links.last.href);
			} else {
				this.onNavigate(response.entity._links.self.href);
			}
		})
	}

	refreshCurrentPage(message) {
		follow(client, root, [{
			rel: 'students',
			params: {
				size: this.state.pageSize,
				page: this.state.page.number
			}
		}]).then(studentCollection => {
			this.links = studentCollection.entity._links;
			this.page = studentCollection.entity.page;

			return studentCollection.entity._embedded.students.map(student => {
				return client({
					method: 'GET',
					path: student._links.self.href
				})
			});
		}).then(studentPromises => {
			return when.all(studentPromises);
		}).then(students => {
			this.setState({
				page: this.page,
				students: students,
				attributes: Object.keys(this.schema.properties),
				pageSize: this.state.pageSize,
				links: this.links
			});
		});
	}
	// end::websocket-handlers[]
	
	updatePageSize(pageSize) {
		if (pageSize !== this.state.pageSize) {
			this.loadFromServer(pageSize);
		}
	}

	// tag::register-handlers[]
	componentDidMount() {
		this.loadFromServer(this.state.pageSize);
		stompClient.register([
			{route: '/topic/newStudent', callback: this.refreshAndGoToLastPage},
			{route: '/topic/updateStudent', callback: this.refreshCurrentPage},
			{route: '/topic/deleteStudent', callback: this.refreshCurrentPage}
		]);
	}

	render() {
		return (
			<div>
				<CreateDialog attributes={this.state.attributes} onCreate={this.onCreate}/>
				<StudentList page={this.state.page}
							  students={this.state.students}
							  links={this.state.links}
							  pageSize={this.state.pageSize}
							  attributes={this.state.attributes}
							  onNavigate={this.onNavigate}
							  onUpdate={this.onUpdate}
							  onDelete={this.onDelete}
							  updatePageSize={this.updatePageSize} />
			</div>
		)
	}
}

export default StudentPage;