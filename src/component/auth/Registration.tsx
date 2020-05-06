import React, {Component} from "react";
import axios from "axios";
import {UserData} from "../../models/UserData";

interface IProps {
  handleSuccessfulAuth: (data: UserData) => void
}

interface IState {
  email: string,
  password: string,
  password_confirmation: string,
  registrationErrors: string
}

export default class Registration extends Component<IProps> {

  state: IState = {
    email: "",
    password: "",
    password_confirmation: "",
    registrationErrors: ""
  }

  public handleInputChange = (event: React.ChangeEvent<HTMLInputElement>): void => {
    this.setState({ ...this.state,
      [event.target.name]: event.target.value 
    });
  }

  public handleSubmit = (event: React.FormEvent<HTMLFormElement>): void => {
    axios.post("https://todo-list-backend-rails.herokuapp.com/registrations", {
      user: {
        email: this.state.email,
        password: this.state.password,
        password_confirmation: this.state.password_confirmation
      }
    }, 
    {withCredentials: true})
    .then(response => {
      if (response.data.status === "created") {
        this.props.handleSuccessfulAuth(response.data);
      }
    })
    .catch(error => {
      console.log(error);
      alert("Username exists or password entered does not match");
    });
    event.preventDefault();
  }

  public render() {
    return (
      <div className = "modal-body">
        <div className = "modal-dialog">
          <div className = "modal-content">

            {/*Modal Header*/}
            <div className = "modal-header">
              <h6 className = "modal-title">Register</h6>
              <button type = "button" className = "close" data-dismiss = "modal">
                &times;
              </button>
            </div>

            {/*Modal Actual*/}
            <div className = "modal-body">
              <form onSubmit = {this.handleSubmit}>
                <div className = "form-group">
                  <input
                    type = "email"
                    name = "email"
                    placeholder = "Email"
                    value = {this.state.email}
                    onChange = {this.handleInputChange}
                    required
                    />
                </div>
                <div className = "form-group">
                  <input
                    type = "password"
                    name = "password"
                    placeholder = "Password"
                    value = {this.state.password}
                    onChange = {this.handleInputChange}
                    required
                    />
                </div>
                <div className = "form-group">
                  <input
                    type = "password"
                    name = "password_confirmation"
                    placeholder = "Password confirmation"
                    value = {this.state.password_confirmation}
                    onChange = {this.handleInputChange}
                    required
                    />
                  </div>
                  <button
                    className = "btn btn-primary btn-sm"
                    type = "submit">REGISTER</button>
              </form>
            </div>

            {/*Modal Footer*/}
            <div className = "modal-footer">
              <button type = "button" className = "btn btn-danger"
                data-dismiss = "modal">
                Close
              </button>
            </div>

          </div>
        </div>
      </div>
    );
  }
}
