import { Modal, Button } from "react-bootstrap";

const CommonModal = ({ children, isPopupShow, openAddClosePopupHandler }) => {
  return (
    <>
      <Modal
        show={isPopupShow}
        onHide={openAddClosePopupHandler}
        backdrop="static"
        keyboard={false}
      >
        <Modal.Header closeButton>
          <Modal.Title>Add vendor</Modal.Title>
        </Modal.Header>
        <Modal.Body>{children}</Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={openAddClosePopupHandler}>
            Close
          </Button>
          <Button variant="primary">Submit</Button>
        </Modal.Footer>
      </Modal>
    </>
  );
};

export default CommonModal;
