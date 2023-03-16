import Breadcrum from "../components/Breadcrum";
import { useNavigate } from "react-router-dom";
import { Button, Container, Row, Col } from "react-bootstrap";

function Dashboard() {
  const navigate = useNavigate();
  const breadcrumMenu = {};

  return (
    <div>
      <Breadcrum breadcrumMenu={breadcrumMenu}/>
      <Container fluid className="mt-3">
        <Row>
          <Col className="col-xs-12 col-sm-12 col-lg-12">
            <div className="mb-2">
              <Button
                variant="primary"
                size="lg"
                onClick={() => {
                  navigate("/vendor-system-details");
                }}
              >
                Vendor System Details
              </Button>{" "}
              <Button
                variant="primary"
                size="lg"
                onClick={() => {
                  navigate("/sponsor-details");
                }}
              >
                Sponsor Details
              </Button>{" "}
              <Button
                variant="primary"
                size="lg"
                onClick={() => {
                  navigate("/site-org-details");
                }}
              >
                Site Org Details
              </Button>{" "}
              <Button
                variant="primary"
                size="lg"
                onClick={() => {
                  navigate("/mapping-details");
                }}
              >
                Mapping Sponsor & Site Org
              </Button>
            </div>
          </Col>
        </Row>
      </Container>
    </div>
  );
}

export default Dashboard;
