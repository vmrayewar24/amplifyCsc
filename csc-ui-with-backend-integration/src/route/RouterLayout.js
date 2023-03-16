import { Outlet } from "react-router-dom";
import Header from "../components/Header";
import Footer from "../components/Footer";

function RouterLayout() {
  return (
    <>
      <div className="d-flex flex-column min-vh-100">
        <Header />
        <Outlet />
        <Footer />
      </div>
    </>
  );
}
export default RouterLayout;
