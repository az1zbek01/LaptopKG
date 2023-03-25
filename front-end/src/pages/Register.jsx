import React, {useState} from 'react';
import {useLocalStorage} from "../hooks/useLocalStorage";
import AuthService from "../API/AuthService";
import {useNavigate} from "react-router-dom";
import MyButton from "../components/UI/button/MyButton";
import {Link} from "react-router-dom";
import {useAuth} from "../context/AuthProvider";

import {
  Card,
  CardHeader,
  CardBody,
  CardFooter,
  Typography,
} from "@material-tailwind/react";
import FormInput from '../components/UI/input/FormInput';
 
const Register = () => {
  const navigate = useNavigate();
  const {signup} = useAuth();
  const [values, setValues] = useState({
    email: "",
    username: "",
    firstName: "",
    lastName: "",
    phoneNumber: "",
    address: "",
    password: "",
    confirmPassword: ""
  });
  const [errors, setErrors] = useState({
    email: "",
    username: "",
    firstName: "",
    lastName: "",
    phoneNumber: "",
    address: "",
    password: "",
    confirmPassword: ""
  })

  const inputs = [
    {
      id: "email",
      name: "email",
      type: "email",
      label: "Email",
      required: true,
    },
    {
      id: "username",
      name: "username",
      type: "text",
      label: "Username",
      required: true,
    },
    {
      id: "firstName",
      name: "firstName",
      type: "text",
      label: "First name",
      required: false,
      pattern: "",
    },
    {
      id: "lastName",
      name: "lastName",
      type: "text",
      label: "Last name",
      required: false,
    },
    {
      id: "phoneNumber",
      name: "phoneNumber",
      type: "tel",
      label: "Phone number",
      required: false,
    },
    {
      id: "address",
      name: "address",
      type: "text",
      label: "Address",
      required: false,
    },
    {
      id: "password",
      name: "password",
      type: "password",
      label: "password",
      required: true,
    },
    {
      id: "confirmPassword",
      name: "confirmPassword",
      type: "password",
      label: "Confirm password",
      required: true,
    },
  ]

  const onChange = (e) => {
    setValues({...values, [e.target.name]: e.target.value});
    // console.log();
  } 


  const sendLoginRequest = (e) => {
    e.preventDefault()
    console.log(values);
    
    const requestBody = values;
    const response = signup(requestBody);
    response.then(() => {
      navigate("/")
    }).catch((response) => {
        console.log(response.response.data.errors)
        setErrors({
          email: "",
          username: "",
          firstName: "",
          lastName: "",
          phoneNumber: "",
          address: "",
          password: "",
          confirmPassword: ""
        })
        for(let error of response.response.data.errors){
          console.log(error);
          let kyz = Object.keys(error);
          const key = kyz[0];
          const value = error[key];
          setErrors(prevErrors => (
            { ...prevErrors,
              [key]: value}
            ));
        }
        if(values['confirmPassword'] !== values['password']){
          setErrors(prevErrors => (
            { ...prevErrors,
              ['confirmPassword']: 'passwords should match'}
            ));
        }

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
          Sign Up
        </Typography>
      </CardHeader>
      <CardBody className="flex flex-col gap-4">
        {
          inputs.map((input) => (
            <FormInput 
            key={input.id} 
            {...input}
            errorMessage={errors[input.name]} 
            value={values[input.name]} 
            onChange={onChange}/>
          ))
        }
      
      </CardBody>
      <CardFooter className="pt-0">
        {/* <MyButton id="submit" type="button" onClick={sendLoginRequest}>Login</MyButton> */}
        <MyButton variant="gradient" onClick={sendLoginRequest} fullWidth>
          Sign up
        </MyButton>
        <Typography variant="small" className="mt-6 flex justify-center">
          Already have an account?
          <Typography
            as="span"
            variant="small"
            color="blue"
            className="ml-1 font-bold"
          >
            <Link to="/login">Sign in</Link> 

          </Typography>
        </Typography>
      </CardFooter>
    </Card>
        </div>
    
  );
}

// const Register = () => {

//     // const [jwt, setJwt] = useLocalStorage("", 'jwt');
//     //
//     //
//     // const register = async () => {
//     //     if(!jwt){
//     //         const response = AuthService.register({
//     //             firstName: 'Fred',
//     //             lastName: 'Flintstone',
//     //             email: 'Flintstone@gmail.com',
//     //             password: 'pass'}
//     //         ).then((response) => response.data.token)
//     //         const token = await response;
//     //         setJwt(token);
//     //     }
//     // }
//     //
//     // useEffect(  () => {
//     //     register();
//     // }, [register])
//     //
//     // useEffect( () => {
//     //     console.log(`jwt - ${jwt}`);
//     // }, [jwt])


//     return (
//         <div>
//             <input type="text"/>
//             <input type="text"/>
//         </div>
//     );
// };

export default Register;