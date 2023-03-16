import "../assets/css/Header.css";
import Card from "react-bootstrap/Card";

function Header() {
  return (
    <Card className="rounded-0 border-top-0 border-bottom-0">
      <Card.Header className="bg-white border-0 p-3">
        <div>
          <div className="d-inline-block header-logo">
            <span className="d-block mb-0 headerFont">cognizant</span>
            <span className="headerFontLogo">clinical systems connector</span>
          </div>

          <div className="float-end d-inline-flex">
            <div className="position-relative me-2">
              <h6 className="m-0 h6-strong" title="Jonathan Bell">
                Jonathan Bell
              </h6>
              <p className="m-0 userDetails">
                <span title="curie.user2@outlook1.com">
                  curie.user2@outlook1.com
                </span>
                <span className="m-0 d-block" title="User Id : studyadmin">
                  User Id : sponsoradmincurie
                </span>
              </p>
            </div>
            <div className="position-relative mt-2">
              <div className="profileImage">JB</div>
            </div>
          </div>
        </div>
      </Card.Header>
    </Card>
  );
}

export default Header;
