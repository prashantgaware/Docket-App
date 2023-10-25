import React, { useState, useEffect } from "react";
import "./DocketForm.css";
import Axios from "axios";

const MyComponent = () => {
  const [selectedSupplier, setSelectedSupplier] = useState("");
  const [selectedPurchaseOrder, setSelectedPurchaseOrder] = useState("");
  const [purchaseOrderDetails, setPurchaseOrderDetails] = useState("");

  function DocketForm() {
    const [name, setName] = useState("");
    const [startTime, setStartTime] = useState();
    const [endTime, setEndTime] = useState();
    const [workingHours, setWorkingHours] = useState();
    const [ratePerHour, setRatePerHour] = useState();
    const [selectedSupplier, setSelectedSupplier] = useState("");
    const [selectedPurchaseOrder, setSelectedPurchaseOrder] = useState("");

    Axios.post("http://localhost:9999/saveDoc").then((res) => {
      let temp = res.data;
    }).catch = (e) => {
      console.log("Error", e);
    };
  }

  //   const suppliers =[]// ['Supplier A', 'Supplier B', 'Supplier C']; // Add your list of suppliers here
  const [suppliers, setSuppliers] = useState([]);
  const [purchaseOrders, setPurchaseOrders] = useState([]);
  //   const purchaseOrders = {
  //     'Supplier A': ['PO-001', 'PO-002'],
  //     'Supplier B': ['PO-003', 'PO-004'],
  //     'Supplier C': ['PO-005', 'PO-006']
  //   }; // Add your purchase orders here
  const [purchaseOrderDescriptions, setPurchaseOrderDescriptions] =
    useState("");
  //   const purchaseOrderDescriptions = {
  //     'PO-001': 'Description for PO-001',
  //     'PO-002': 'Description for PO-002',
  //     'PO-003': 'Description for PO-003',
  //     'PO-004': 'Description for PO-004',
  //     'PO-005': 'Description for PO-005',
  //     'PO-006': 'Description for PO-006'
  //   }; // Add your purchase order descriptions here

  const handleSupplierChange = (event) => {
    const selectedSupplier = event.target.value;
    console.log("selected supploierd -----" + selectedSupplier);

    Axios.post("http://localhost:9999/getPo", selectedSupplier, {
      headers: { "Content-Type": "application/text" },
    })
      .then((response) => {
        setPurchaseOrders(response.data);
        console.log("Post successful:", response.data);
      })
      .catch((error) => {
        console.error("Error posting data:", error);
      });

    setSelectedSupplier(selectedSupplier);
    setSelectedPurchaseOrder("");
    setPurchaseOrderDetails(null);
  };

  const handlePurchaseOrderChange = (event) => {
    const selectedPurchaseOrder = event.target.value;
    setSelectedPurchaseOrder(selectedPurchaseOrder);
    Axios.post("http://localhost:9999/getPoDesc", selectedPurchaseOrder, {
      headers: { "Content-Type": "application/text" },
    })
      .then((response) => {
        //   setPurchaseOrders(response.data)
        setPurchaseOrderDetails(response.data);
        console.log("Post successful:", response.data);
      })
      .catch((error) => {
        console.error("Error posting data:", error);
      });
  };

  useEffect(() => {
    // Make a GET request to your service
    Axios.get("http://localhost:9999/getSupplierDetails")
      .then((response) => {
        // Assuming response.data is an array of options
        console.log(response);
        console.log(response.data);
        // setOptions(resresponse.dataponse.data);
        setSuppliers(response.data);
        // setPoNum(response.data);
        // setPoDesc(response.data);
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
      });
  }, []);

  return (
    //onSubmit={handleSubmit}

    <div className="docket-form">
      <h1>Docket Form</h1>
      <form>
        <label>
          Name:
          <input type="text" name="name" />
        </label>
        <br />

        <label>
          Start Time:
          <input type="time" name="startTime" />
        </label>
        <br />

        <label>
          End Time:
          <input type="time" name="endTime" />
        </label>
        <br />

        <label>
          No. of Hours Worked:
          <input type="number" name="hoursWorked" />
        </label>
        <br />

        <label>
          Rate per Hour:
          <input type="number" name="ratePerHour" step="0.01" />
        </label>
        <br />
        <div>
          <label>Supplier:</label>
          <select value={selectedSupplier} onChange={handleSupplierChange}>
            <option value="">Select a supplier</option>
            {suppliers.map((supplier, index) => (
              <option key={index} value={supplier}>
                {supplier}
              </option>
            ))}
          </select>

          {selectedSupplier && (
            <div>
              <label>Purchase Order:</label>
              <select
                value={selectedPurchaseOrder}
                onChange={handlePurchaseOrderChange}>
                <option value="">Select a purchase order</option>
                {purchaseOrders.map((po, index) => (
                  <option key={index} value={po}>
                    {po}
                  </option>
                ))}

                {/* {purchaseOrders[selectedSupplier].map((po, index) => (
              <option key={index} value={po}>
                {po}
              </option>
            ))} */}
              </select>
            </div>
          )}

          {selectedPurchaseOrder && (
            <div>
              <h3>Purchase Order Details</h3>
              <p>Number: {selectedPurchaseOrder}</p>
              <p>Description: {purchaseOrderDetails}</p>
            </div>
          )}
        </div>

        <button type="submit" onSubmit={DocketForm}>
          Create Docket
        </button>
      </form>
    </div>
  );
};

//   const [options, setOptions] = useState([]);
//   const [poNum, setPoNum] = useState([]);
//   const [poDesc, setPoDesc] = useState([]);

//   useEffect(() => {
//     // Make a GET request to your service
//     Axios.get('http://localhost:9999/getSupplierDetails')
//       .then((response) => {
//         // Assuming response.data is an array of options
//         console.log(response)
//         console.log(response.data)
//         setOptions(response.data);
//         setPoNum(response.data);
//         setPoDesc(response.data);
//       })
//       .catch((error) => {
//         console.error('Error fetching data:', error);
//       });
//   }, []); // Empty dependency array ensures this effect runs once on component mount

//   return (
//     <div>
//       <label>Select an option:</label>
//       <select value={options} onChange={poNum}>
//         {options.map((option) => (
//           <option key={option} value={option}>
//             {option}
//           </option>
//         ))}
//       </select>
//     </div>
//   );
// };

export default MyComponent;
