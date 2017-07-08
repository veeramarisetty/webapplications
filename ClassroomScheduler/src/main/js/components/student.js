'use strict';
import React from 'react';
import ReactDOM from 'react-dom';
import UpdateDialog from './updateDialog';

class Student extends React.Component {

	constructor(props) {
		super(props);
		this.handleDelete = this.handleDelete.bind(this);
	}

	handleDelete() {
		this.props.onDelete(this.props.student);
	}

	render() {
		return (
			<tr>
				<td>{this.props.student.entity.firstName}</td>
				<td>{this.props.student.entity.lastName}</td>
				<td>{this.props.student.entity.email}</td>
				<td>{this.props.student.entity.gender}</td>
				<td>{this.props.student.entity.currentYear}</td>
				<td>{this.props.student.entity.currentSemester}</td>
				<td>{this.props.student.entity.department}</td>
				<td>{this.props.student.entity.joinDate}</td>
				<td>{this.props.student.entity.graduationYear}</td>
				<td>
					<UpdateDialog student={this.props.student}
								  attributes={this.props.attributes}
								  onUpdate={this.props.onUpdate} />
				</td>
				<td>
					<button onClick={this.handleDelete}>Delete</button>
				</td>
			</tr>
		)
	}
}

export default Student;