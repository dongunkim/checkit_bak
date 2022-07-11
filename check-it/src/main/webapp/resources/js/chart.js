/**
 * [jquery chart.js]를 활용한 chart.js
 * @author devkimsj
 */
Date.prototype.format = function(f) {
		if (!this.valueOf()) return " ";

		var weekName = ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"];
		var d = this;

		return f.replace(/(yyyy|yy|MM|dd|E|HH|hh|mm|ss|a\/p)/gi, function($1) {
			switch ($1) {
			case "yyyy": return d.getFullYear();
			case "yy": return (d.getFullYear() % 1000).zf(2);
			case "MM": return (d.getMonth() + 1).zf(2);
			case "dd": return d.getDate().zf(2);
			case "E": return weekName[d.getDay()];
			case "HH": return d.getHours().zf(2);
			case "hh": return ((h = d.getHours() % 12) ? h : 12).zf(2);
			case "mm": return d.getMinutes().zf(2);
			case "ss": return d.getSeconds().zf(2);
			case "a/p": return d.getHours() < 12 ? "오전" : "오후";
			default: return $1;
			}
		});
	};

String.prototype.string = function(len){var s = '', i = 0; while (i++ < len) { s += this; } return s;};
String.prototype.zf = function(len){return "0".string(len - this.length) + this;};
Number.prototype.zf = function(len){return this.toString().zf(len);};

commChart = {
		/**
		 * 차트 만들기
		 */
		init : function(cfg, callBackFn){

			let _this = this;
			let option = {
					url: cfg.url,
					data: (typeof cfg.params === "object")?$.param(cfg.params):cfg.params,
					type: "post",
					cache: false,
					dataType: "json",
					contentType: "application/x-www-form-urlencoded; charset=UTF-8;",
					success: function(chartData) {

						_this.makeChart(chartData.result, cfg);
						let parentArea = $("#" + cfg.targetId).parents(".chart_list_area");
						if(parentArea.find(".chart_info").length == 0){
							parentArea.find(".chart_view").css("width", "100%");
						}

						if($.type(callBackFn) === "function"){
							callBackFn(chartData.result);
						}

					},
					error: function(req, status, error) {
					},
					beforeSend: function(xhr){
						xhr.setRequestHeader("ajax", true);
					},
					complete: function(){
					}
			};
			$.ajax(option);

		}
		,
		makeChart : function(chartData, cfg){
			let _this = this;
			let targetId  = cfg.targetId;
			let ctx = document.getElementById(targetId).getContext("2d");

			let isStacked = false;
			let isFill    = false;
			let tacked    = false;
			if(cfg.chartType == "stacked"){
				isStacked = true;
				isFill    = true;
				cfg.chartType = "bar";
			}else if(cfg.chartType == "area"){
				isStacked = true;
				isFill    = true;
				cfg.chartType = "line";
			}else if(cfg.chartType == "time"){
				cfg.chartType = "line";
			}

			let datasets = [];
			let labels   = [];
			let mappigData = [];
			if($.type(chartData) === "undefined"){
				let canvas = document.getElementById(targetId);
					canvas.width = window.innerWidth;
				let ctx = canvas.getContext("2d");
				let x = canvas.width/2;
				let y = canvas.height/2;
					ctx.font = "20px malgun gothic"; //폰트의 크기, 글꼴체 지정
					ctx.textAlign  = "center";
					ctx.fillText("등록 된 데이터가 없습니다.", x, y);
				if($("#" + targetId).next().prop("tagName") == "IMG"){
					$("#" + targetId).next().remove();
				}
				let errconfig = {
						type: cfg.chartType,
//						data: {
//							labels: labels,
//							datasets: datasets
//						},
						options: {
							responsive: true,
							title: {
								 display: true
								,text: cfg.title
							},
							tooltips: {
								 mode: "index"
								,intersect: true
							},
							hover: {
								 mode: "index"
								,intersect: false
							},
							scales: {
								xAxes: [{
									 display: true
									,stacked: isStacked
									,scaleLabel: {
										display: true,
//										labelString: cfg.xLabel
									}
								}],
								yAxes: [{
									 display: true
									,stacked: isStacked
									,scaleLabel: {
										display: true,
//										labelString: yLabel
									}
		//							,
		//							ticks: {
		//								min: 0,
		//								max: 100,
		//								// forces step size to be 5 units
		//								stepSize: 5
		//							}
								}]
							}
						}
					};
				chart = new Chart(ctx, errconfig);
				
	            return false;
			}else{

				if(chartData.length > 0){

					if($("#" + targetId).next().prop("tagName") == "IMG"){
						$("#" + targetId).next().remove();
					}

					let mappingData = {};
					let maxVal = 0;
					$.each(chartData, function(resultIndex, resultValue){
						labels.push(_this.dateType(resultValue[cfg.labels], cfg.dateFormat, cfg.dateType));
						$.each(cfg.dataArray, function(i, v){
							if($.type(mappingData[v.label]) === "undefined"){
								mappingData[v.label] = [];
							}else{
								mappingData[v.label].push(resultValue[v.dataName]);
							}
						});
					});

					$.each(mappingData, function(mappingIdx, mappingArrData){
						let max = mappingArrData.reduce(function(previous, current){
							return previous > current ? previous:current;
						});

						if(maxVal < max){
							maxVal = max;
						}
					});

					let minMaxAvg = {};
					let unit   = "";

					$.each(mappingData, function(mappingIdx, mappingArrData){

						let color = "";
						$.each(cfg.dataArray, function(cfgIdx, cfgData){
							if(cfgData.label == mappingIdx){
								color = cfgData.color;
							}
						});

						let max = mappingArrData.reduce(function(previous, current){
							return previous > current ? previous:current;
						});

						let min = mappingArrData.reduce(function(previous, current){
							return previous > current ? current:previous;
						});

						let sum = mappingArrData.reduce(function(previous, current){
							return previous + current;
						});

						let avg = sum / mappingArrData.length;

						let divisionVal = 1;
						// GBit로 계산
						if(maxVal > 1000000000){
							divisionVal = 1000000000;
							unit = "Gb/초";
						// MBit로 계산
						}else if(maxVal < 1000000000 && maxVal > 1000000){
							divisionVal = 1000000;
							unit = "Mb/초";
						}else{
							divisionVal = 1000;
							unit = "Kb/초";
						}

						minMaxAvg[mappingIdx] = {
												 label: mappingIdx
												,color: color
												,max: max
												,min: min
												,avg: avg
												,last: mappingArrData[mappingArrData.length - 1]
												};

					});

					$("#" + targetId).next().remove()

					let html = [];
					// 최대값/ 평균값 / 현재
					let minMaxAvgIdx = 1;
						html.push("<div  id=\"" + targetId + "InfoArea\">");
					$.each(minMaxAvg, function(idx, data){

						let maxUnit, avgUnit, lastUnit;
						// GBit로 계산
						if(data.max > 1000000000){
							data.max = (data.max / 1000000000).toFixed(1);
							maxUnit = "Gb/초";
						// MBit로 계산
						}else if(data.max < 1000000000 && data.max > 1000000){
							data.max = (data.max / 1000000).toFixed(1);
							maxUnit = "Mb/초";
						}else if(data.max < 1000000 && data.max > 1000){
							data.max = (data.max / 1000).toFixed(1);
							maxUnit = "Kb/초";
						}else{
							data.max = Math.round(data.max);
							maxUnit = "b/초";
						}

						// GBit로 계산
						if(data.avg > 1000000000){
							data.avg = (data.avg / 1000000000).toFixed(1);
							avgUnit = "Gb/초";
						// MBit로 계산
						}else if(data.avg < 1000000000 && data.avg > 1000000){
							data.avg = (data.avg / 1000000).toFixed(1);
							avgUnit = "Mb/초";
						}else if(data.avg < 1000000 && data.avg > 1000){
							data.avg = (data.avg / 1000).toFixed(1);
							avgUnit = "Kb/초";
						}else{
							data.avg = Math.round(data.avg);
							avgUnit = "b/초";
						}

						// GBit로 계산
						if(data.last > 1000000000){
							data.last = (data.last / 1000000000).toFixed(1);
							lastUnit = "Gb/초";
						// MBit로 계산
						}else if(data.last < 1000000000 && data.last > 1000000){
							data.last = (data.last / 1000000).toFixed(1);
							lastUnit = "Mb/초";
						}else if(data.last < 1000000 && data.last > 1000){
							data.last = (data.last / 1000).toFixed(1);
							lastUnit = "Kb/초";
						}else{
							data.last = Math.round(data.last);
							lastUnit = "b/초";
						}

						html.push("<div style=\"width:100%;font-size:12px;text-align:center;font-weight: 1000;color:" + data.color + ";\">");
						html.push("<div style=\"float:left;width:35%;text-align:right;\">최대 " + data.label + " : " + data.max + " " + maxUnit + "</div>");
						html.push("<div style=\"float:left;width:30%;\">평균 " + data.label + " " + data.avg + " " + avgUnit + "</div>");
						if(minMaxAvgIdx == Object.keys(minMaxAvg).length){
							html.push("<div style=\"float: left; width: 20%;text-align:left;\">현재 " + data.label + " : " + data.last + " " + lastUnit + "</div>");
							html.push("<div style=\"float: left; width: 15%;text-align:right; position: absolute;right: 1%; bottom: 0.5%\"><button class=\"button_sh w80 m1_button_2 gray\" id=\"" + targetId + "CsvDownBtn\"><span>CSV저장</span></button></div>");
						}else{
							html.push("<div style=\"float: left; width: 35%;text-align:left;\">현재 " + data.label + " : " + data.last + " " + lastUnit + "</div>");
						}

						html.push("</div>");
						minMaxAvgIdx++;
					});
					html.push("</div>");

					$("#" + targetId).after(html.join(""));

					_this.csvDownloadFn(cfg, chartData)

					let setDatas = [];
					let yLabel = cfg.yLabel;
					$.each(cfg.dataArray, function(i, v){
						let setData = {};
						let divisionVal = 1;

						if(cfg.yLabel == "auto"){
							// GBit로 계산
							if(maxVal > 1000000000){
								divisionVal = 1000000000;
								yLabel = "Gbit/s"
							// MBit로 계산
							}else if(maxVal < 1000000000 && maxVal > 1000000){
								divisionVal = 1000000;
								yLabel = "Mbit/s"
							}else{
								divisionVal = 1000;
								yLabel = "Kbit/s"
							}
						}

						$.each(chartData, function(resultIndex, resultValue){
							if($.type(setData[v.dataName]) === "undefined"){
								setData[v.dataName] = [];
							}
							setData[v.dataName].push((resultValue[v.dataName] / divisionVal).toFixed(2));
						});

						let dataset = {
								 label : v.label
								,pointRadius: (($.type(cfg.pointRadius) === "undefined")?0:cfg.pointRadius)
								,backgroundColor : v.color
								,borderColor : v.color
								,data : setData[v.dataName]
								,fill: isFill
						}
						datasets.push(dataset);
					});

					let config = {
						type: cfg.chartType,
						data: {
							labels: labels,
							datasets: datasets
						},
						options: {
							responsive: true,
							title: {
								 display: true
								,text: cfg.title
							},
							tooltips: {
								 mode: "index"
								,intersect: true
							},
							hover: {
								 mode: "index"
								,intersect: false
							},
							scales: {
								xAxes: [{
									 display: true
									,stacked: isStacked
									,scaleLabel: {
										display: true,
										labelString: cfg.xLabel
									}
								}],
								yAxes: [{
									 display: true
									,stacked: isStacked
									,scaleLabel: {
										display: true,
										labelString: yLabel
									}
		//							,
		//							ticks: {
		//								min: 0,
		//								max: 100,
		//								// forces step size to be 5 units
		//								stepSize: 5
		//							}
								}]
							}
						}
					};

					let charts;
					if($.type(Window.chart) === "undefined"){
						charts = Window.chart = [];
					}else{
						charts = Window.chart;
					}

					let	chart;
					if($.type(charts[targetId]) === "undefined"){
						chart = charts[targetId] = [];
					}else{
						chart = charts[targetId];
						chart.destroy();
					}

					chart = new Chart(ctx, config);
					charts[targetId] = chart;

				}else{
					let canvas = document.getElementById(targetId);
						canvas.width = window.innerWidth;
					let ctx = canvas.getContext("2d");
					let x = canvas.width/2;
					let y = canvas.height/2;
						ctx.font = "20px malgun gothic"; //폰트의 크기, 글꼴체 지정
						ctx.textAlign  = "center";
						ctx.fillText("등록 된 데이터가 없습니다.", x, y);
					if($("#" + targetId).next().prop("tagName") == "IMG"){
						$("#" + targetId).next().remove();
					}
					
					let config = {
							type: cfg.chartType,
//							data: {
//								labels: labels,
//								datasets: datasets
//							},
							options: {
								responsive: true,
								title: {
									 display: true
									,text: cfg.title
								},
								tooltips: {
									 mode: "index"
									,intersect: true
								},
								hover: {
									 mode: "index"
									,intersect: false
								},
								scales: {
									xAxes: [{
										 display: true
										,stacked: isStacked
										,scaleLabel: {
											display: true,
//											labelString: cfg.xLabel
										}
									}],
									yAxes: [{
										 display: true
										,stacked: isStacked
										,scaleLabel: {
											display: true,
//											labelString: yLabel
										}
			//							,
			//							ticks: {
			//								min: 0,
			//								max: 100,
			//								// forces step size to be 5 units
			//								stepSize: 5
			//							}
									}]
								}
							}
						};
					chart = new Chart(ctx, config);
//					charts[targetId] = chart;
					
		            return false;
				}

			}

		}
		,
		colors : {
			 red    : "rgb(255, 99, 132)"
			,orange : "rgb(255, 159, 64)"
			,yellow : "rgb(255, 205, 86)"
			,green  : "rgb(75, 192, 192)"
			,blue   : "rgb(54, 162, 235)"
			,purple : "rgb(153, 102, 255)"
			,grey   : "rgb(201, 203, 207)"
		}
		,
		chartType : {
			 line : "line"
			,bar  : "bar"
			,stck : "stacked"
			,area : "area"
			,time : "time"
		}
		,
		/**
		 * @param timestemp 타임스템프
		 * @param dateFormat 데이터 타입 (yyyy년 MM월 dd일 a/p hh시 mm분 ss초 E)
		 */
		dateType : function(timestemp, dateFormat, dateType){

			let date;
			if($.type(dateType) === "undefined"){
				date = new Date(timestemp * 1000);
			}else{
				
				let year  = timestemp.substring(0, 4).number();
				let month = timestemp.substring(4, 6).number() - 1;
				let day   = timestemp.substring(6, 8).number();
				
				if(month == 0){
					year = year - 1;
					month = "12";
				}else if(month < 10){
					month =  "0" + month;
				}
				
				date = new Date(year, month, day);
				
				if(timestemp.length == 14){
					let hours = timestemp.substring(8, 10).number();
					let minutes = timestemp.substring(10, 12).number();
					let seconds = timestemp.substring(12).number();
					date.setHours(hours);
					date.setMinutes(minutes);
					date.setSeconds(seconds);
				}
			}

			let format;
			if($.type(dateFormat) === "undefined"){
				format = "yyyy.MM.dd HH:mm:ss";
			}else{
				format = dateFormat;
			}

			return date.format(format);
		}
		,
		csvDownloadFn : function(cfg, list) {
			let targetId = cfg.targetId;
			let _this = this;
			$("#" + targetId + "CsvDownBtn").wrap("<a id=\"" + targetId + "csvLinkBtn\"></a>");

			let header  = [];
			let csvData = [];
			let dateColName = cfg.labels;
			let inColName   = cfg.dataArray[0].dataName;
			let outColName  = cfg.dataArray[1].dataName;
			let title       = cfg.title;

			csvData.push("\uFEFF");
			$.each(list, function(objIdx, obj){

				let tmpStr = [];
				if(objIdx == 0){
					header.push(title);
					csvData.push(header.join(","));
					header  = [];
					header.push("일시");
					header.push("수신");
					header.push("송신");
				}

				let dateCol = obj[dateColName].substring(0,4)+"."+obj[dateColName].substring(4,6)+"."+obj[dateColName].substring(6,8)
							+ " "+ obj[dateColName].substring(8,10)+":"+obj[dateColName].substring(10,12)+":"+obj[dateColName].substring(12);
				tmpStr.push(dateCol);
				tmpStr.push(obj[inColName]);
				tmpStr.push(obj[outColName]);

				if(objIdx == 0){
					csvData.push(header.join(","));
				}

				csvData.push(tmpStr.join(","));
			});

			let output = csvData.join("\n");
			let filename = title + ".csv";

			// IE 10, 11, Edge Run
			if (window.navigator && window.navigator.msSaveOrOpenBlob) {
				let blob = new Blob([decodeURIComponent(output)], {
					type: "text/csv;charset=utf8"
				});

				$("#" + targetId + "CsvDownBtn").unbind().bind("click", function(){
					window.navigator.msSaveOrOpenBlob(blob, filename);
				});

			}else if(window.Blob && window.URL) {
				// HTML5 Blob
				let blob = new Blob([output], { type: "text/csv;charset=utf8" });
				let csvUrl = URL.createObjectURL(blob);

				$("#" + targetId + "csvLinkBtn").attr({
					 download : filename
					,href : csvUrl
					,target : "_blank"
				});

			}else{
				// Data URI
				let csvData = "data:application/csv;charset=utf-8," + encodeURIComponent(output);
				let blob = new Blob([output], { type: "text/csv;charset=utf8" });
				let csvUrl = URL.createObjectURL(blob);

				$("#" + targetId + "csvLinkBtn").attr({
					 download : filename
					,href : csvUrl
					,target : "_blank"
				});

			}

		}
}