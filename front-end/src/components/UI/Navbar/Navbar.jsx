import MyButton from "../button/MyButton";
import {Link} from "react-router-dom";

const Navbar = () => {

    return (
        <div className="navbar">
            <MyButton onClick={() => {}}>
                Выйти
            </MyButton>
            <div className="navbar__links">
                <Link to="/about">About</Link>
                <Link to="/posts">Posts</Link>
            </div>
        </div>
    );
};

export default Navbar;