import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Example from "./components/ExampleComponent";

function App() {
  return (
    <Router>
      <div className="flex justify-center items-center min-h-screen">
        <Routes>
          <Route path="/example" element={<Example />} />
          <Route path="/" element={<div>LANDING PAGE</div>} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
