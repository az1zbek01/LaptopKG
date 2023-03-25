import React from 'react';

import { Button } from "@material-tailwind/react";

const MyButton = ({children, ...props}) => {
    return (
        <Button {...props} className="">
            {children}
        </Button>
    );
};

export default MyButton;