import React,{useState} from 'react';
import './login.css';
import Logo from '../assets/Images/Logo.png';
import logdrag from '../assets/Images/logindragon.png';
import back from '../assets/Images/Back.png';
import { Link,useNavigate} from 'react-router-dom';

function Login() {
      const navigate = useNavigate();
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    password: ''
  });

const handleSubmit = async (e) => {
    e.preventDefault();

    if (formData.username && formData.email && formData.password) {
        try {
            const res = await fetch('http://localhost:5001/api/users/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    email: formData.email,
                    password: formData.password
                })
            });

            const data = await res.json();

            if (res.ok) {

                localStorage.setItem("user", JSON.stringify(data.user));//save userinfo in local storage for profile page

                alert('Login successful!');
                navigate('/home');
            } else {
                alert(data.message || 'Login failed');
            }
        } catch (err) {
            console.error(err);
            alert('Server error');
        }
    } else {
        alert('Please fill in all fields');
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

        <div className='leftcontainer'>
                          {/* Back button */}
            <button
            onClick={() => window.history.back()}
            style={{
            position: 'absolute',
            top: '20px',
            left: '35%',
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
            <img src={logdrag} alt="Dragon" className="logdragon-image" />
        </div>

        <div className='rightcontainer'>

            <h1 style={{ 
                textAlign: 'center', 
                top: '10%',
                position: 'absolute',
                fontSize: '40px',
                fontWeight: 'bold',
                color: '#2A0C3E',
            }}>Access to your account</h1>
            <form className='login-form' onSubmit={handleSubmit}>
                <input type="text"
                name='username'
                placeholder="Username" 
                className='input-field' 
                value={formData.username} 
                onChange={handleChange} required /><br />

                <input type="email" 
                name='email'
                placeholder="Email" 
                className='input-field' 
                value={formData.email} 
                onChange={handleChange} required /><br />

                <input type="password"
                 name='password'
                 placeholder="Password" 
                 className='input-field' 
                 value={formData.password} 
                 onChange={handleChange} required /><br />

                <button type="submit" 
                className='login-button'>Login</button>

            </form>
            <p style={{ 
                textAlign: 'center', 
                marginTop: '20px',
                color: '#2A0C3E',
                fontSize: '24px'}
            }>Don't have an account? <Link to="/signin">Sign In</Link></p>

        </div>
    </div>
  );

}

export default Login;
