import React, {useState} from 'react';
import {useLocalStorage} from "../hooks/useLocalStorage";
import AuthService from "../API/AuthService";
import {useNavigate} from "react-router-dom";

const Login = () => {
    const navigate = useNavigate();
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [jwt, setJwt] = useLocalStorage("", 'jwt');

    const sendLoginRequest = (e) => {
        e.preventDefault()
        const requestBody = {
            email: email,
            password: password
        }
        const response = AuthService.authenticate(requestBody);
        response.then(response => {
            setJwt(response.data.token);
            navigate("/about")
        }).catch((message) => {
            alert(message);
        })

    }

    return (
        <div>
            <div>
                <label htmlFor="email">email</label>
                <input
                    type="email"
                    id='email'
                    value={email}
                    onChange={(event) =>  setEmail(event.target.value)}/>
            </div>
            <div>
                <label htmlFor="password">password</label>
                <input
                    type="password"
                    id='password'
                    value={password}
                    onChange={(event) =>  setPassword(event.target.value)}/>
            </div>
            <div>
                <button id="submit" type="button" onClick={sendLoginRequest}>Login</button>
            </div>
        </div>



    );
};

export default Login;