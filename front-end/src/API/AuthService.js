import axios from 'axios';


export default class AuthService {

    static async register(requestBody){
        const response = await axios.post('http://localhost:8080/api/v1/auth/register', requestBody);
        return response;
    }

    static async authenticate(requestBody){
        const response = await axios.post('http://localhost:8080/api/v1/auth/authenticate', requestBody);
        return response;
    }
}