import Card from "react-bootstrap/Card";
import "../assets/css/Footer.css";

function Footer() {
  return (
    <div className="text-lg-left mt-auto">
      <Card className="rounded-0 border-top-0">
        <Card.Footer className="border-0">
          <span className="copyRight">© 2023, All rights reserved.</span>
          <div className="float-end">
            <span className="footerLinks">Privacy Policy</span>
            <span className="footerLinks">Terms of Use</span>
            <span className="footerLinks">Support</span>
            <span className="footerLinks">Help Desk</span>
          </div>
        </Card.Footer>
      </Card>
    </div>
  );
}

export default Footer;
