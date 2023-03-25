import './App.css';
import {BrowserRouter} from "react-router-dom";
import AppRouter from "./components/AppRouter";
import MyNavbar from './components/UI/Navbar/MyNavbar';

function App() {

    return (
        <BrowserRouter>
        <MyNavbar/>
        <AppRouter/>
    </BrowserRouter>
    

    );
}

export default App;
