<!doctype html public "-//w3c//dtd html 4.0 transitional//en">

<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/dataFilterSlider" prefix="ez" %>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>

<html>
    <head>
        <title></title>
        <style type="text/css">

            body{
                font: normal 11px tahoma, sans-serif;
            }

            .column{
                width:75px;
                font: normal 11px tahoma, sans-serif;
                text-align:center;
            }

            .column-index{
                width:75px;
                font: normal 11px tahoma, sans-serif;
                text-align:left;
            }

            .list-row3{
                background-color:#ececec;
            }

            .list-row1{
                background-color:#f1f6fd;
            }

            .list-row2{
                background-color:#fff;
            }

            .list-header{
                font: bold 11px tahoma, sans-serif;
                text-align:center;
            }

            .list-table1{
                border:1px solid #bed6f8;
            }

            .list-table2{
                border:1px solid #bed6f8;
            }

            
            
        </style>

    </head>
    <body>
        <f:view>
              <%--<ez:dataFilterSlider sliderListener="#{mybean.doSlide}"
                  onSlide="true"
                  onChange="true"
                  storeResults="false"
                  trackStyleClass="track"
                  styleClass="Mytest"
                  startRange="0"
                  endRange="50000"
                  increment="10000"
                  rangeStyleClass="range"
                  trailer="true"
                  trailerStyleClass="trailer"
                  handleStyleClass="handle"
                  handleValue="1"
                  id="slider_1" ></ez:dataFilterSlider>--%>
                <a4j:form id="form1" reRender="list-body" ajaxSubmit="true" ignoreDupResponses="true" requestDelay="100">

                <h:selectOneRadio binding="#{skinBean.component}" />
                <h:commandLink action="#{skinBean.change}" value="set skin" />

                <a4j:region id="stat1">
                    
                <a4j:outputPanel id="slider-body">
                    
                    <ez:dataFilterSlider  sliderListener="#{mybean.doSlide}"
                                    binding="#{inventoryList.dataFilterSlider}"
                                    for="carList"
                                    forValRef="inventoryList.carInventory"
                                    filterBy="getMileage"
                                    manualInput="true"
                                    onslide="true"
                                    onchange="true"
                                    storeResults="true"
                                    trackStyleClass="track"
                                    width="400px"
                                    styleClass="slider-container"
                                    startRange="10000"
                                    endRange="60000"
                                    increment="10000"
                                    rangeStyleClass="range"
                                    trailer="true"
                                    trailerStyleClass="trailer"
                                    handleStyleClass="handle"
                                    handleValue="10000"
                                    id="slider_1" ></ez:dataFilterSlider>

               </a4j:outputPanel>


               <a4j:outputPanel id="list-body">
               <f:verbatim>

               </f:verbatim>
                <h:dataTable id="carIndex"
                        rows="10"
                        binding="#{inventoryList.carMakeIndexUIData}"
                        value="#{inventoryList.carMakeIndex}"
                        var="category"
                        styleClass="list-table1"
                        columnClasses="column-index"
                        rowClasses="list-row3">

                 <h:column>
                         <a4j:commandLink actionListener="#{inventoryList.filterCarList}" reRender="carList">
                              <h:outputText value="#{category}"/>
                              <f:attribute name="filterRule" value="showTable"/>
                             
                         </a4j:commandLink>
                </h:column>
                
                </h:dataTable>

                <h:dataTable id="carList"
                        rows="10"
                        value="#{inventoryList.carInventory}"
                        var="category"
                        rowClasses="list-row1, list-row2"
                        columnClasses="column"
                        headerClass="list-header"
                        styleClass="list-table2">

                <h:column>
                    <f:facet name="header">
                            <h:outputText styleClass="headerText" value="Make"/>
                    </f:facet>
                    <h:outputText value="#{category.make}"/>
                </h:column>
                <h:column>
                    <f:facet name="header">
                            <h:outputText styleClass="headerText" value="Model"/>
                    </f:facet>
                    <h:outputText value="#{category.model}"/>
                </h:column>
                <h:column>
                    <f:facet name="header">
                            <h:outputText styleClass="headerText" value="#{inventoryList.priceColumnName}"/>
                    </f:facet>
                    <h:outputText value="#{category.price}"/>
                </h:column>
                 <h:column>
                    <f:facet name="header">
                        <h:outputText styleClass="headerText" value="#{inventoryList.mileageColumnName}"/>
                    </f:facet>
                    <h:outputText value="#{category.mileage}"/>
                </h:column>

                </h:dataTable>
               <f:verbatim>
               
               </f:verbatim>
                 <!--<ac:ajaxDataScroller id="scroll_1"-->
                                     <!--for="carList"-->
                                     <!--maxPages="9">-->
                <!--</ac:ajaxDataScroller>-->

                </a4j:outputPanel>



             </a4j:region>

        </a4j:form>

        </f:view>
    </body>
</html>
