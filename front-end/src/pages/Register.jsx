import React, {useEffect} from 'react';
import {useLocalStorage} from "../hooks/useLocalStorage";
import AuthService from "../API/AuthService";

const Register = () => {

    // const [jwt, setJwt] = useLocalStorage("", 'jwt');
    //
    //
    // const register = async () => {
    //     if(!jwt){
    //         const response = AuthService.register({
    //             firstName: 'Fred',
    //             lastName: 'Flintstone',
    //             email: 'Flintstone@gmail.com',
    //             password: 'pass'}
    //         ).then((response) => response.data.token)
    //         const token = await response;
    //         setJwt(token);
    //     }
    // }
    //
    // useEffect(  () => {
    //     register();
    // }, [register])
    //
    // useEffect( () => {
    //     console.log(`jwt - ${jwt}`);
    // }, [jwt])


    return (
        <div>
            <input type="text"/>
            <input type="text"/>
        </div>
    );
};

export default Register;