import {createContext, useState, useContext, useEffect } from "react";
import { useLocalStorage } from "../hooks/useLocalStorage";
import api from "../API/api";
import { useNavigate } from "react-router-dom";

const AuthContext = createContext(null);

export function useAuth() {
  return useContext(AuthContext);
}

export function AuthProvider({ children }) {
  const [jwt, setJwt] = useLocalStorage("", "jwt");
  const [loading, setLoading] = useState(true);
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  

  async function signup(requestBody){
    const response = await api.post('/api/auth/register', requestBody);
    // localStorage.setItem("jwt", JSON.stringify(response.data.token));
    setIsAuthenticated(true);
    setJwt(response.data.token);
    return response;
  }

  async function login(requestBody) {
    const response = await api.post('/api/auth/authenticate', requestBody);

    // localStorage.setItem("jwt", JSON.stringify(response.data.token));
    setIsAuthenticated(true);
    setJwt(response.data.token);
    console.log(response);
    return response;
  }

  function logout() {
    localStorage.setItem("jwt", "");
    setIsAuthenticated(false);
  }

  useEffect(() => {
    if(jwt){
      setIsAuthenticated(true);
    }
    // TODO: check if user is already authenticated using JWT token
    setLoading(false);
  }, []);

  const value = {
    signup,
    login,
    logout,
    isAuthenticated,
  };

  return (
    <AuthContext.Provider value={value}>
      {!loading && children}
    </AuthContext.Provider>
  );
}