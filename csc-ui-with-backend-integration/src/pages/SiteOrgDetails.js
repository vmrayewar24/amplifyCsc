import Breadcrum from "../components/Breadcrum";
import { Container, Row, Col } from "react-bootstrap";

const SiteOrgDetails = () => {
  const breadcrumMenu = {
    name : "Site Organization Details",
    link : "#"
  };
  return (
    <>
      <Breadcrum breadcrumMenu={breadcrumMenu}  />
      <Container fluid className="mt-3">
        <Row>
          <Col className="col-xs-12 col-sm-12 col-lg-12">
            <p>Site Organization Details</p>
          </Col>
        </Row>
      </Container>
    </>
  );
};

export default SiteOrgDetails;
