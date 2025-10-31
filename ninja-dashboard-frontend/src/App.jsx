import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Layout from './components/Layout';
import Dashboard from './pages/Dashboard';
import Personajes from './pages/Personajes';
import Aldeas from './pages/Aldeas';
import Jutsus from './pages/Jutsus';

function App() {
  return (
    <Router>
      <Layout>
        <Routes>
          <Route path="/" element={<Dashboard />} />
          <Route path="/personajes" element={<Personajes />} />
          <Route path="/aldeas" element={<Aldeas />} />
          <Route path="/jutsus" element={<Jutsus />} />
        </Routes>
      </Layout>
    </Router>
  );
}

export default App;