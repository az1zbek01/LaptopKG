import {useEffect, useState} from "react";

function useLocalStorage (defaultValue, key){
    const [value, setValue] = useState( () => {
        const localStorageVal = localStorage.getItem(key);
        return localStorageVal !== null ? JSON.parse(localStorageVal) : defaultValue;
        }
    );
    // console.log(`localStorageJWT value is: ${value}`)
    useEffect(() => {
        localStorage.setItem(key, JSON.stringify(value))
        // console.log(`localStorageJWT updated value is: ${value}`)
    }, [key, value])

    return [value, setValue];
}
export {useLocalStorage}