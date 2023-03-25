import api from './api';


export default class AuthService {

    static async register(requestBody){
        const response = await api.post('/api/auth/register', requestBody);
        return response;
    }

    static async authenticate(requestBody){
        const response = await api.post('/api/auth/authenticate', requestBody);
        return response;
    }
    static async getInfoUser(){
        const response = await api.get('/api/test/getUser');
        return response;
    }
    static async getInfoAdmin(){
        const response = await api.get('/api/test/getAdmin');
        return response;
    }
}