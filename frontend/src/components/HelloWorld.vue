<template>
    <div>
      <div v-if="mainBoardList.length > 0">
        <button @click="openModal">Open Modal</button>
      </div>
      <div v-else>No data received yet.</div>
      <button @click="reqApi">Request API</button>
      <button @click="stopApi">Stop API</button>
  
      <!-- 모달 창 -->
      <div class="modal" :class="{ 'is-active': showModal }">
        <div class="modal-background" @click="closeModal"></div>
        <div class="modal-content">
          <div>
            <ul class="list-group" style="max-height: 300px; overflow-y: auto">
              <li
                class="list-group-item"
                v-for="(item, index) in mainBoardList"
                :key="index"
              >
                {{ item }}
              </li>
            </ul>
          </div>
          <button @click="closeModal">Close Modal</button>
        </div>
      </div>
      <div>
        <label for="eventSourceUrl">EventSource URL:</label>
        <input type="text" id="eventSourceUrl" v-model="eventSourceUrl" />
        <button @click="applyEventSource">Apply</button>
      </div>
  
      <!-- 요청 시작 및 종료 시간 -->
      <div v-if="startTime">Request started at: {{ startTime }}</div>
      <div v-if="stopTime">Request stopped at: {{ stopTime }}</div>
  
      <!-- Count 입력 -->
      <div>
        <label for="count">Count:</label>
        <input type="number" id="count" v-model="count" />
      </div>
    </div>
  
    <div>
      <label for="num">Num:</label>
      <input type="number" id="num" v-model="newData" />
      <button @click="addData">Add</button>
    </div>
  
    <!-- 그래프를 보여줄 canvas 엘리먼트 추가 -->
    <div>
      <canvas ref="chartCanvas" id="myChart"></canvas>
    </div>
  </template>
  
  <script>
  import { Chart, registerables } from "chart.js";
  import { ref } from "vue";
  
  Chart.register(...registerables);
  // const chartContainer = ref(null);
  const chartCanvas = ref(null);
  const array2 = [10, 30, 20, 30, 15];
  
  export default {
    data() {
      return {
        mainBoardList: [],
        showModal: false,
        eventSourceUrl: "http://localhost:8009/api/emitter",
        eventSource: null,
        startTime: null,
        stopTime: null,
        count: 0,
        newData: 0,
      };
    },
    methods: {
      reqApi() {
        // 요청 시작 시간 기록
        this.startTime = new Date().toLocaleTimeString();
  
        let receivedCount = 0;
  
        this.eventSource = new EventSource(this.eventSourceUrl);
  
        this.eventSource.onmessage = (event) => {
          const data = event.data.trim();
  
          if (data.startsWith("Hello, World!")) {
            const number = parseFloat(data.replace("Hello, World!", "").trim());
  
            if (!isNaN(number)) {
              this.mainBoardList.push(event.data);
              receivedCount++;
              console.log(number);
              this.updateChart(number);
            }
          }
  
          if (receivedCount >= this.count) {
            this.eventSource.close();
            // 요청 종료 시간 기록
            this.stopTime = new Date().toLocaleTimeString();
          }
        };
  
        this.eventSource.onerror = (error) => {
          console.error("EventSource failed:", error);
          this.eventSource.close();
        };
      },
      stopApi() {
        if (this.eventSource) {
          this.eventSource.close();
          this.eventSource = null;
          // 요청 종료 시간 기록
          this.stopTime = new Date().toLocaleTimeString();
        }
      },
      addData() {
        // 새로운 데이터를 그래프에 추가하는 메서드
        const a = document.getElementById("num");
        const numValue = a.value;
        array2.push(numValue);
        console.log(array2);
        // 차트 데이터 업데이트
        this.updateChart();
      },
      openModal() {
        this.showModal = true;
      },
      closeModal() {
        this.showModal = false;
      },
      applyEventSource() {
        this.reqApi();
      },
      updateChart(number) {
        const ctx = document.getElementById("myChart");
        const chart = Chart.getChart(ctx);
  
        if (chart) {
          console.log("UPDATE CHART");
          var tmp = [];
          for (var i = 0; i < 100; i++) {
            tmp[i] = array2[i];
          }
          for (var j = 0; j < 99; j++) {
            array2[j] = tmp[j + 1];
          }
          array2[99] = number;
          chart.update();
          console.log("Array : " + array2);
          console.log("Length : " + array2[4]);
        }
      },
    },
    mounted() {
      console.log("Mounted");
      console.log(chartCanvas);
      console.log(chartCanvas.value);
      var arr3 = [];
      for (var i = 0; i < 100; i++) {
        array2[i] = Math.round(Math.random() * 10);
        arr3[i] = i;
      }
  
      const ctx = document.getElementById("myChart");
      const chart = new Chart(ctx, {
        type: "line",
  
        data: {
          labels: arr3,
  
          datasets: [
            {
              label: "Demo Data",
  
              backgroundColor: "rgba(255, 99, 132, 0.2)",
  
              borderColor: "rgba(255, 99, 132, 1)",
  
              data: array2,
  
              fill: true,
            },
          ],
        },
  
        options: {
          responsive: true,
          animation: {
            duration: 0,
          },
  
          scales: {
            y: {
              beginAtZero: true,
            },
          },
        },
      });
      console.log(chart);
      // 컴포넌트가 마운트된 후에 차트 생성
      // this.openModal();
    },
  };
  </script>
  
  <style>
  .modal {
    display: none;
  }
  
  .modal.is-active {
    display: flex;
    align-items: center;
    justify-content: center;
  }
  
  .modal-background {
    position: fixed;
    top: 0;
    left: 0;
    width: 60%;
    height: 10%;
  }
  
  .modal-content {
    background-color: white;
    padding: 20px;
    border-radius: 5px;
    max-width: 80%;
    max-height: 80%;
    overflow: auto;
  }
  
  .list-group {
    margin: 0;
    padding: 0;
    list-style: none;
  }
  
  .list-group-item {
    padding: 10px;
  }
  </style>