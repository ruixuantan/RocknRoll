import React, {Component} from "react";
import axios from "axios";
import {UserData} from "../../models/UserData";

interface IProps {
  handleSuccessfulAuth: (data: UserData) => void
}

interface IState {
  email: string,
  password: string,
  loginErrors: string
}

export default class Login extends Component <IProps>{

  state: IState = {
    email: "",
    password: "",
    loginErrors: ""
  };

  public handleInputChange = (event: React.ChangeEvent<HTMLInputElement>): void => {
    this.setState({ ...this.state,
      [event.target.name]: event.target.value 
    });
  }

  public handleSubmit = (event: React.FormEvent<HTMLFormElement>): void => {
    axios.post("https://todo-list-backend-rails.herokuapp.com/sessions", {
      user: {
        email: this.state.email,
        password: this.state.password
        }
      },
      {withCredentials: true})
    .then(response => {
      if (response.data.logged_in) {
        this.props.handleSuccessfulAuth(response.data);
      } else {
        alert("Password is wrong or username doesn't exist");
      }
    })
    .catch(error => {
      console.log("login error", error);
    });
    event.preventDefault();
  }

  public render() {
    return (
      <div>
        <h5>Login</h5>
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
          <button
            className = "btn btn-primary"
            type = "submit">LOGIN</button>
        </form>
      </div>
    );
  }
}
