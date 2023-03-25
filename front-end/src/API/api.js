import axios from "axios";
import { useLocalStorage } from "../hooks/useLocalStorage";

const api = axios.create({
  baseURL: "http://localhost:8080",
  
});

// api.defaults.headers.common['Authorization'] = `Bearer ${localStorage.getItem('jwt')}`

api.interceptors.request.use((config) => {
  const jwt = localStorage.getItem("jwt");
  if (jwt) {
    config.headers['Authorization'] = `Bearer ${JSON.parse(jwt)}`;
  }
  return config;
});

export default api;