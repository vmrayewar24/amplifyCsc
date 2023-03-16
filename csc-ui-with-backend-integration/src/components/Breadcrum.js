import { Breadcrumb } from "react-bootstrap";
import { Link } from "react-router-dom";
import "../assets/css/Breadcrumb.css";

function Breadcrum(props) {
  console.log(props);
  return (
    <div className="breadcrumbContainer px-3">
      <Breadcrumb>
        <Breadcrumb.Item>
          <Link to="/">Dashboard</Link>{" "}
        </Breadcrumb.Item>
        {props.breadcrumMenu.name !== "" && (
          <Breadcrumb.Item>
            <Link to={props.breadcrumMenu.link}>
              {props.breadcrumMenu.name}
            </Link>{" "}
          </Breadcrumb.Item>
        )}
      </Breadcrumb>
    </div>
  );
}

export default Breadcrum;
