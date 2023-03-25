import React from 'react';
import {Navigate, Route, Routes} from "react-router-dom";
import About from "../pages/About";
import JwtPage from "../pages/JwtPage";
import Register from "../pages/Register";
import Login from "../pages/Login";
import PrivateRoute from "../util/PrivateRoute";
import CreateLaptop from '../pages/Admin/CreateLaptop';


const AppRouter = () => {

    return (

        <Routes>

            <Route path="/login" key="/login" element={<Login/>}/>
            <Route path="/register" key="/register" element={<Register/>}/>
            <Route path="/" element={<JwtPage/>}></Route>
            <Route path="/about" element={
                <PrivateRoute>
                    <About/>
                </PrivateRoute>
            }></Route>
            <Route path="/laptop/create" element={
                <PrivateRoute>
                    <CreateLaptop/>
                </PrivateRoute>
            }></Route>
            <Route path="/register" element={<Register/>}></Route>
            <Route path="/*" key="/*" element={<Navigate to="/Error" replace />} />
        </Routes>
    );
};

export default AppRouter;