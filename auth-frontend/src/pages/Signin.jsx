import React,{useState} from 'react';
import './login.css';
import Logo from '../assets/Images/Logo.png';
import signdrag from '../assets/Images/signindragon.png';
import back from '../assets/Images/Back.png';
import {Link, useNavigate} from 'react-router-dom';

function Signin() {
    const navigate = useNavigate();

    const handleBack = () => {
        navigate('/');
    };

    const [formData, setFormData] = useState({
        firstname: '',
        lastname: '',
        username: '',
        email: '',
        password: ''
    });

const handleSubmit = async (e) => {
    e.preventDefault();

    if (formData.firstname && formData.lastname && formData.username && formData.email && formData.password) {
        try {
            const res = await fetch('http://localhost:5001/api/users/register', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(formData)
            });

            const data = await res.json();

            if (res.ok) {
                alert('Registration successful!');
                navigate('/login');
            } else {
                alert(data.message || 'Error registering user');
            }
        } catch (err) {
            console.error(err);
            alert('Server error');
        }
    } else {
        alert('Please fill all fields');
    }
};


    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prevState => ({
            ...prevState,
            [name]: value
        }));
    };
  return(
    <div className='login-container'>
        <img src={Logo} alt="Logo" className="logo" />

        <div className='rightcontainer' style={{borderRadius:'0 80px 80px 0'}}>
            {/*adjusted for left in signin*/}
            <h1 style={{ 
                textAlign: 'center', 
                top: '10%',
                position: 'absolute',
                fontSize: '40px',
                fontWeight: 'bold',
                color: '#2A0C3E',
                paddingBottom: '20px',
            }}>Create your account</h1><br />
            <br />
            <form className='login-form' onSubmit={handleSubmit}>

                <input type="text"
                name='firstname'
                placeholder="Firstname" 
                className='signin-input-field' 
                value={formData.firstname} 
                onChange={handleChange} required /><br />

                <input type="text" 
                name='lastname'
                placeholder="Lastname" 
                className='signin-input-field' 
                value={formData.lastname} 
                onChange={handleChange} required /><br />

                <input type="text" 
                name='username'
                placeholder="Username" 
                className='signin-input-field' 
                value={formData.username} 
                onChange={handleChange} required /><br />

                <input type="email" 
                name='email'
                placeholder="Email" 
                className='signin-input-field' 
                value={formData.email} 
                onChange={handleChange} required /><br />

                <input type="password" 
                name='password'
                placeholder="Password" 
                className='signin-input-field' 
                value={formData.password} 
                onChange={handleChange} required /><br />

                <button type="submit" className='login-button'>Sign Up</button>
            </form>

            <p style={{ 
                textAlign: 'center', 
                marginTop: '20px',
                color: '#2A0C3E',
                fontSize: '24px'}
            }>Already have an account? <Link to="/login">Log In</Link></p>

        </div>

        <div className='leftcontainer'>
            {/*adjusted for right in signin*/}
                          {/* Back button */}
            <button
            onClick={handleBack}
            style={{
            position: 'absolute',
            top: '20px',
            right: '35%',
            background: 'none',
            border: 'none',
            cursor: 'pointer',
            fontSize: '16px',
            }}  
            >
            <img 
            src={back}
            alt="Back" 
            style={{ width: '25px', height: '25px', marginRight: '5px' }} 
            />
            </button>
            <img src={signdrag} alt="Dragon" style={{right:'5%',
                position: 'relative',
                top:'15%',
                width: 'auto',
                height: '80%',
            }}/>

        </div>
    </div>
  );

}

export default Signin;
