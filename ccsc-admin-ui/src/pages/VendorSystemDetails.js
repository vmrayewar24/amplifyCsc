import Breadcrum from "../components/Breadcrum";
import {
  Container,
  Row,
  Col,
  Button,
  Table,
  Badge,
  Form,
} from "react-bootstrap";
import { useState } from "react";
import CommonModal from "../components/CommonModal";

function VendorSystemDetails() {
  const [popupShow, setPopupShow] = useState(false);
  const [vendorSystemCategory, setVendorSystemCategory] = useState("");
  const [typeOfService, setTypeOfService] = useState("");

  const openAddOpenPopupHandler = () => {
    setPopupShow(true);
  };

  const openAddClosePopupHandler = () => {
    console.log("hiiii");
    setPopupShow(false);
  };

  const breadcrumMenu = {
    name: "Vendor System Details",
    link: "#",
  };

  const addModalBody = (
    <Form>
      <Form.Group className="mb-3">
        <Form.Label>Vendor System</Form.Label>
        <Form.Control type="text" placeholder="Vendor Systems" />
      </Form.Group>

      <Form.Group className="mb-3">
        <Form.Label>Vendor System Category</Form.Label>

        <Form.Control
          placeholder="Vendor Systems"
          as="select"
          value={vendorSystemCategory}
          onChange={(e) => {
            console.log("e.target.value", e.target.value);
            setVendorSystemCategory(e.target.value);
          }}
        >
          <option value="">--Select--</option>
          <option value="eTMF">eTMF</option>
          <option value="eISF">eISF</option>
          <option value="Site Portal">Site Portal</option>
          <option value="RTSM">RTSM</option>
          <option value="EDC">EDC</option>
          <option value="LMS">LMS</option>
          <option value="Safety">Safety</option>
        </Form.Control>
      </Form.Group>

      <Form.Group className="mb-3">
        <Form.Label>Type of Service Values</Form.Label>

        <Form.Control
          placeholder="Type of Service Values"
          as="select"
          value={typeOfService}
          onChange={(e) => {
            console.log("e.target.value", e.target.value);
            setTypeOfService(e.target.value);
          }}
        >
          <option value="">--Select--</option>
          <option value="1">Send Approved Study Documents</option>
          <option value="2">Receive Approved Study Documents</option>
          <option value="3">
            Send Approved & Intermediate Study Documents
          </option>
          <option value="4">
            Receive Approved & Intermediate Study Documents
          </option>
          <option value="5">Send Safety Notifications</option>
          <option value="6">Receive Safety Notifications</option>
        </Form.Control>
      </Form.Group>
    </Form>
  );

  return (
    <>
      <Breadcrum breadcrumMenu={breadcrumMenu} />
      {/* Start of add vendor system modal */}
      <CommonModal
        isPopupShow={popupShow}
        openAddClosePopupHandler={openAddClosePopupHandler}
      >
        {" "}
        {addModalBody}{" "}
      </CommonModal>

      {/* End of add vendor system modal */}

      <Container fluid className="mt-3">
        <Row>
          <Col className="col-xs-12 col-sm-12 col-lg-12">
            <Button
              variant="primary"
              className="float-end d-inline-flex mr-3"
              onClick={openAddOpenPopupHandler}
            >
              +Add
            </Button>
          </Col>
        </Row>
        <Row>
          <Col className="col-xs-12 col-sm-12 col-lg-12">
            <Table
              striped
              bordered
              hover
              className="connectionRequestTable mt-3"
            >
              <thead>
                <tr>
                  <th>ID#</th>
                  <th>Vendor Systems</th>
                  <th>Vendor System Category</th>
                  <th>Type of Service Values</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>RFC76767</td>
                  <td>Florence</td>
                  <td>eTMF</td>
                  <td>Send Approved Study Documents</td>
                  <td>
                    <Badge pill bg="primary" style={{ cursor: "pointer" }}>
                      {" "}
                      View
                    </Badge>{" "}
                    <Badge pill bg="danger" style={{ cursor: "pointer" }}>
                      {" "}
                      Delete
                    </Badge>
                  </td>
                </tr>
                <tr>
                  <td>UYC76767</td>
                  <td>Veeva</td>
                  <td>CTMS</td>
                  <td>Send Approved & Intermediate Study Documents</td>
                  <td>
                    <Badge pill bg="primary" style={{ cursor: "pointer" }}>
                      {" "}
                      View
                    </Badge>{" "}
                    <Badge pill bg="danger" style={{ cursor: "pointer" }}>
                      {" "}
                      Delete
                    </Badge>{" "}
                  </td>
                </tr>
              </tbody>
            </Table>
          </Col>
        </Row>
      </Container>
    </>
  );
}

export default VendorSystemDetails;
