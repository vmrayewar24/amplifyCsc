import Breadcrum from "../components/Breadcrum";
import { Container, Row, Col } from "react-bootstrap";

const SponsorDetails = () => {
  
    const breadcrumMenu = {
      name : "Sponsor Details",
      link : "#"
    };
  return (
    <>
      <Breadcrum breadcrumMenu={breadcrumMenu}  />
      <Container fluid className="mt-3">
        <Row>
          <Col className="col-xs-12 col-sm-12 col-lg-12">
            <p>Sposor Details</p>
          </Col>
        </Row>
      </Container>
    </>
  );
};

export default SponsorDetails;
