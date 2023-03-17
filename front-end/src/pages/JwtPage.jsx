import React from 'react';
import {useLocalStorage} from "../hooks/useLocalStorage";

const JwtPage = () => {

    const [jwt, setJwt] = useLocalStorage("", 'jwt');


    return (
        <div>
            <header>
                <h4>${jwt}</h4>
            </header>
        </div>
    );
};

export default JwtPage;