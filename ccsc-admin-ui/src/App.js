import "./App.css";
import "bootstrap/dist/css/bootstrap.min.css";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import RouterLayout from "./route/RouterLayout";
import Dashboard from "./pages/Dashboard";
import VendorSystemDetails from "./pages/VendorSystemDetails";
import SponsorDetails from "./pages/SponsorDetails";
import MappingDetails from "./pages/MappingDetails";
import SiteOrgDetails from "./pages/SiteOrgDetails";


const router = createBrowserRouter([
  {
    path: "/",
    element: <RouterLayout />,
    children: [
     { path: "/", element: <Dashboard /> },
     { path: "/vendor-system-details", element: <VendorSystemDetails /> },
     { path: "/sponsor-details", element: <SponsorDetails /> },
     { path: "/site-org-details", element: <SiteOrgDetails /> },
     { path: "/mapping-details", element: <MappingDetails /> }
    ],
  },
]);

function App() {
  return (
    <RouterProvider router={router} />
  );
}

export default App;
