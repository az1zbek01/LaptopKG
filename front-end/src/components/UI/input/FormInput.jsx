import React from 'react';
import { Input, } from "@material-tailwind/react";

const FormInput = (props) => {
    const {onChange, errorMessage, ...inputProps} = props;
    return ( 
        <div>
           <Input 
            {...inputProps}
            onChange={onChange}
            size="lg"
            className=''
            /> 
            {errorMessage &&
                <span className='text-xs p-1 text-red-500'>{errorMessage}</span>
            }
        </div>
        
     );
}
 
export default FormInput;