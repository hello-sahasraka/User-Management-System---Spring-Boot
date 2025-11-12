import { Route, Routes } from "react-router-dom"
import Login from "./pages/login"
import Admin from "./pages/admin/Admin"
import User from "./pages/user/User"
import ProtectedRoute from "./ProtectedRoute"

function App() {

  return (
    <div>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/admin" element={<ProtectedRoute><Admin /></ProtectedRoute>} />
        <Route path="/user" element={<User />} />
      </Routes>
    </div>
  )
}

export default App
