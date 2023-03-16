import Breadcrum from "../components/Breadcrum";
import {
  Container,
  Row,
  Col,
  Button,
  Table,
  Badge,
  Modal,
  Form,
} from "react-bootstrap";
import React, { useState, useEffect } from "react";
import {API} from 'aws-amplify';


function VendorSystemDetails() {
  const [popupShow, setPopupShow] = useState(false);
  const [popupShowDelete, setPopupShowDelete] = useState(false);
  const [vendorId, setVendorId] = useState('');

  const breadcrumMenu = {
    name : "Vendor System Details",
    link : "#"
  };

  const [serviceValues, setServiceValues] = useState({});
  const api_path_1 = "/serv";
  const api_name_1 ="servAPI";
  const getServiceValues = async() => {
      await API.get(api_name_1,api_path_1).then(data=>{
          setServiceValues(data);
          console.log(data);
      }).catch(error=>{
          console.log("Error");
      });
  }



  const [vendor, setVendor] = useState({});
  const api_path = "/vsd";
  const api_name ="vsdAPI";
  const getVendor = async() => {
      await API.get(api_name,api_path).then(data=>{
        
        data.forEach(element => {
          serviceValues.forEach(ele => {
            if(element.servtype===ele.id){
              element.servtypename=ele.name;
              console.log("HIIIIIIIIIIIIJJJ",element);
            }
          });
        });
        setVendor(data);

      }).catch(error=>{
          console.log("Error");
      });
  }

 

  useEffect(() => {
      getVendor();
      getServiceValues();
  },[]);

  const initialState = {
    id:'',
    name: '',
    category: '',
    servtype: '',
  };
  const [vsd, setVsd] = useState(initialState);

  const resetState = () => {
    setVsd(initialState);
  };

  const handleSubmit = async(e) => {
      e.preventDefault();
      if(vsd.id!==''){
        await API.put(api_name,api_path,{body:vsd}).then(data=>{
          //history('/vendor-system-details');
          setPopupShow(false);
          console.log(data);
        }).catch(error=>{
            setPopupShow(false);
            console.log("Error");
        });
      }else{
        await API.post(api_name,api_path,{body:vsd}).then(data=>{
          setPopupShow(false);
          console.log(data);
        }).catch(error=>{
            setPopupShow(false);
            console.log("Error");
        });
      }
      getVendor();
      
  }

  const onChangeHandler=({target:{id,value}})=>{
    setVsd({...vsd,[id]:value})
  }

  const addNewHandler = () => {
    setPopupShow(true);
    resetState();
  } 

  const handleDelete = async(id) => {
    setPopupShowDelete(true);
    setVendorId(id);
  }

  const handleDeleteConfirm = async() => {
    let obj = {"id":vendorId};
      await API.del(api_name,api_path,{body:obj}).then(data=>{
        setPopupShowDelete(false);
          console.log(data);
      }).catch(error=>{
        setPopupShowDelete(false);  
        console.log("Error");
      });
      getVendor();
  }

  const handleEdit = (id) => {
    getVendorDetails(id);
    setPopupShow(true);
  }

  const getVendorDetails = async(id) => {
    let obj = api_path+"/"+id;
    await API.get(api_name,obj).then(data=>{
        setVsd(data);
        console.log(data);
    }).catch(error=>{
        console.log("Error");
    });
}


  return (
    <>
      <Breadcrum breadcrumMenu={breadcrumMenu} />
      {/* Start of add vendor system modal */}
      <Modal
        show={popupShow}
        onHide={() => setPopupShow(false)}
        backdrop="static"
        keyboard={false}
      >
        <Modal.Header closeButton>
          <Modal.Title>Add vendor</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            <Form.Group className="mb-3">
              <Form.Label>Vendor System</Form.Label>
              <Form.Control type="hidden" id='id' value={vsd.id} onChange={onChangeHandler} />
              <Form.Control type="text" id='name' value={vsd.name} placeholder="Vendor Systems" onChange={onChangeHandler} />
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Label>Vendor System Category</Form.Label>

              <Form.Control
                placeholder="Vendor Systems"
                as="select"
                value={vsd.category}
                id='category'
                onChange={onChangeHandler}
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
                value={vsd.servtype}
                onChange={onChangeHandler}
                id='servtype'
              >
                <option value="">--Select--</option>
              {
                  serviceValues && serviceValues.length > 0
                  ?
                  serviceValues.map((item,index)=> {
                      return(
                        <option value={item.id}>{item.name}</option>
                      )
                  })
                  :
                  "No Data Available"
              }
              </Form.Control>
            </Form.Group>
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setPopupShow(false)}>
            Close
          </Button>
          <Button variant="primary" onClick={handleSubmit}>Submit</Button>
        </Modal.Footer>
      </Modal>

      {/* End of add vendor system modal */}

      {/* Start of delete vendor system modal */}
      <Modal
        show={popupShowDelete}
        onHide={() => setPopupShowDelete(false)}
        backdrop="static"
        keyboard={false}
      >
        <Modal.Header closeButton>
          <Modal.Title>Delete vendor</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          Are you sure you want to <b>delete?</b>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setPopupShowDelete(false)}>
            Close
          </Button>
          <Button variant="primary" onClick={handleDeleteConfirm}>Submit</Button>
        </Modal.Footer>
      </Modal>

      {/* End of delete vendor system modal */}

      <Container fluid className="mt-3">
        <Row>
          <Col className="col-xs-12 col-sm-12 col-lg-12">
            <Button
              variant="primary"
              className="float-end d-inline-flex mr-3"
              onClick={addNewHandler}
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
              {
                  vendor && vendor.length > 0
                  ?
                  vendor.map((item,index)=> {
                      return(
                          <tr key={item.id}>
                              <td>
                                  {item.id}
                              </td>
                              <td>
                                  {item.name}
                              </td>
                              <td>
                                  {item.category}
                              </td>
                              <td>
                                  
                                  {item.servtypename}
                              </td>
                              <td>
                                <Badge pill bg="primary" style={{"cursor":"pointer"}} onClick={()=> handleEdit(item.id)}>
                                  {" "}
                                  Edit
                                </Badge>{" "}
                                <Badge pill bg="danger" style={{"cursor":"pointer"}} onClick={()=> handleDelete(item.id)}>
                                  {" "}
                                  Delete
                                </Badge>
                              </td>
                          </tr>
                      )
                  })
                  :
                  "No Data Available"
              }
                
              </tbody>
            </Table>
          </Col>
        </Row>
      </Container>
    </>
  );
}

export default VendorSystemDetails;
