import React, {useEffect, useState} from 'react';
import {useLocalStorage} from "../hooks/useLocalStorage";
import AuthService from "../API/AuthService";
import {useNavigate, redirect} from "react-router-dom";
import MyButton from "../components/UI/button/MyButton";
import {Link} from "react-router-dom";
import {useAuth} from "../context/AuthProvider";

import {
    Card,
    CardHeader,
    CardBody,
    CardFooter,
    Typography,
    Checkbox,
    Alert,
  } from "@material-tailwind/react";

import FormInput from '../components/UI/input/FormInput';

const Login = () => {
    const navigate = useNavigate();
    const { signup, login, logout, isAuthenticated } = useAuth();
    const [values, setValues] = useState({
      email: "",
      password: ""
    });
    const [isError, setisError] = useState(false);

    const inputs = [
      {
        id: "email",
        name: "email",
        type: "email",
        label: "Email",
        required: true,
      },
      {
        id: "password",
        name: "password",
        type: "password",
        label: "Password",
        required: true,
      },
    ]

    const onChange = (e) => {
      setValues({...values, [e.target.name]: e.target.value});
    }

    const sendLoginRequest = (e) => {
        e.preventDefault()
        const requestBody = values;
        const response = login(requestBody);
        response.then(() => {
          navigate("/");
        }).catch((error) => {
          console.log(error.response.status);
          setisError(false);
          if(error.response.status == 403) setisError(true);
          // alert(response.message);
        })
    }

    return (
        <div className="container mx-auto mt-20">
        <Card className="w-96 mx-auto">
      <CardHeader
        variant="gradient"
        color="blue"
        className="mb-4 grid h-28 place-items-center"
      >
        <Typography variant="h3" color="white">
          Sign In
        </Typography>
      </CardHeader>
      <CardBody className="flex flex-col gap-4">
      
        <Alert 
        show={isError}
        color="red"
        >Wrong password or email.</Alert>
      
        {
          inputs.map((input) => (
            <FormInput 
            key={input.id} 
            {...input}
            value={values[input.name]} 
            onChange={onChange}
            />
          ))
        }
        <div className="-ml-2.5">
          <Checkbox label="Remember Me" />
        </div>
      </CardBody>
      <CardFooter className="pt-0">
        {/* <MyButton id="submit" type="button" onClick={sendLoginRequest}>Login</MyButton> */}
        <MyButton variant="gradient" onClick={sendLoginRequest} fullWidth>
          Sign In
        </MyButton>
        <Typography variant="small" className="mt-6 flex justify-center">
          Don't have an account?
          <Typography
            as="span"
            variant="small"
            color="blue"
            className="ml-1 font-bold"
          >
            <Link to="/register" className="ml-1 font-bold">Sign up</Link> 
          </Typography>
        </Typography>
      </CardFooter>
    </Card>
        </div>
    

        // <div className='container mx-auto'>
        //     <div>
        //         <label htmlFor="email">email</label>
        //         <input
        //             type="email"
        //             id='email'
        //             value={email}
        //             onChange={(event) =>  setEmail(event.target.value)}/>
        //     </div>
        //     <div>
        //         <label htmlFor="password">password</label>
        //         <input
        //             type="password"
        //             id='password'
        //             value={password}
        //             onChange={(event) =>  setPassword(event.target.value)}
        // />
        //     </div>
        //     <div>
        //         <MyButton id="submit" type="button" onClick={sendLoginRequest}>Login</MyButton>
        //     </div>
        // </div>



    );
};

export default Login;