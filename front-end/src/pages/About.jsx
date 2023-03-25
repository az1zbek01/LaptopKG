import React, { useEffect } from 'react';
import AuthService from '../API/AuthService';

const About = () => {

    

    const user = {
        username: "",
        email: "",
        firstName: "",
        lastName: "",
    }

    useEffect(() => {
        const response1 = AuthService.getInfoAdmin();
        console.log(response1);
    }, [])


    return (
        <h1>
            About this app
        </h1>
    );
};

export default About;