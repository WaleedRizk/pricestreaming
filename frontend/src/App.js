import React, { Component } from 'react';
import './App.css';
import Ticker from 'react-ticker'

import { AgGridReact } from 'ag-grid-react';
import 'ag-grid/dist/styles/ag-grid.css';
import 'ag-grid/dist/styles/ag-theme-balham-dark.css';
import * as SockJS from 'sockjs-client';
import Stomp from "stompjs"

class App extends Component {

    constructor(props) {
        super(props);
        this.state = {
            columnDefs: [
                {headerName: 'Instrument', field: 'instrument', editable: false, width: 120, resizable: true},
                {headerName: 'Bid', field: 'bid', editable: false, width: 120, resizable: true,
                cellStyle: function(params) {
                    console.log(params.data);
                    if (params.data.highUp) {
                        //mark police cells as red
                        return {color: 'green'};
                    } else {
                        return {color: 'white'};
                    }
                }},
                {headerName: 'Ask', field: 'ask', editable: false, width: 120, resizable: true,
                cellStyle: function(params) {
                    console.log(params.data);
                    if (params.data.highUp) {
                        //mark police cells as red
                        return {color: 'green'};
                    } else {
                        return {color: 'white'};
                    }
                }},
                {headerName: 'Vendor', field: 'vendor', editable: false, width: 120, resizable: true},
                {headerName: 'Date', field: 'date', editable: false, width: 160, resizable: true, sort: 'desc'}
            ],
            rowData: [],
            unformattedRowData:[],
            mapTickerToRow: []
        }

    }

    connectCallback(json)
    {
        json = JSON.parse(json);
        var tempRowData = [],tempMapTickerToRow=[];
        tempRowData = this.state.unformattedRowData;
        tempMapTickerToRow = this.state.mapTickerToRow;
        var j = JSON.stringify(json[0]);
        console.log(j);

       var y = j.substring(0,5);

       console.log("This is tempRowData",tempRowData);
       console.log("This is tempMapTickerToRow",tempMapTickerToRow);
       console.log("This is json[0]",json[0].bid);
       console.log("This is tempMapTickerToRow",tempMapTickerToRow);



         if(tempMapTickerToRow[json[0].bid]>=0) {
           
             var priceUp = parseFloat(json[0].bid)>=parseFloat(tempRowData[tempMapTickerToRow[json[0].bid.charCodeAt(0)]].price)?true:false;
        //   //  var highUp = parseFloat(json[0].high)>parseFloat(tempRowData[tempMapTickerToRow[json[0].ticker.charCodeAt(0)]].high)?true:false;
        //  //   var lowDown = parseFloat(json[0].low)<parseFloat(tempRowData[tempMapTickerToRow[json[0].ticker.charCodeAt(0)]].low)?true:false;
        //     json[0]['priceUp']=priceUp;
        //  //   json[0]['highUp']=highUp;
        //  //   json[0]['lowDown']=lowDown;
             tempRowData[tempMapTickerToRow[json[0].ticker.charCodeAt(0)]] = json[0];
         }
         else {
        //     json[0]['priceUp']=true;
        //     json[0]['highUp']=false;
        //     json[0]['lowDown']=false;
            tempRowData[tempRowData.length]=json[0];
            tempMapTickerToRow[json[0].vendor.charCodeAt(0)]=tempRowData.length-1;
         }
        this.setState({mapTickerToRow:tempMapTickerToRow});
        this.setState({unformattedRowData:tempRowData});
        var jsonTemp = [];
        tempRowData.forEach(
            t => jsonTemp.push({
                "instrument": t.instrument,
                "bid": t.bid.toLocaleString('en-US',{style:'currency',currency:'USD'}),
                "ask":t.ask.toLocaleString('en-US',{style:'currency',currency:'USD'}),
                "vendor":t.vendor,
                "date":new Date(t.date).toLocaleString('en-US')})
        )
        console.log("This is jsonTemp",jsonTemp);
        this.setState({rowData: jsonTemp});
     }
     componentDidMount() {
         let self = this;
         var stompClient = null;
         var inquirySubscription = null;
         var socket = new SockJS('http://localhost:8081/tickerservice-websocket');
         stompClient = Stomp.over(socket);
         stompClient.connect({}, function (frame) {
             var inquirySub1Var = 122;
             inquirySubscription=stompClient.subscribe('/user/topic/ticker', function (greeting) {
                 self.connectCallback(greeting.body);
             }, {id: inquirySub1Var, userName: "andrewf"});

         });

    }

    render() {
        return (
            <div
                className="ag-theme-balham-dark"
                style={{height: '250px', width: '900px'}}
            >
                <h1><center>Welcome to my Price Streaming Application!!!</center></h1>
            <AgGridReact
                    enableSorting={true}
                    enableFilter={true}
                    pagination={true}
                    paginationPageSize={5}
                    columnDefs={this.state.columnDefs}
                    enableCellChangeFlash={true}
                    animateRows={true}
                    rowData={this.state.rowData}>
            </AgGridReact>
            <Ticker offset="run-in" speed={10}>
                {({ index }) => (
                    

                    <h2>Waleed Rizk </h2>
                    
                )}
            </Ticker>

            
            </div>
        );
    }
}

export default App;