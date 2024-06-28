<script setup>
import {nextTick, ref, watchEffect} from "vue";
// import {socket, chatMessages, id} from "@/socket/socket";
// import ConnectionState from "@/ConnectionState.vue";

import {socket, chatMessages, id} from "@/socket/socket"
import ConnectionState from "@/components/ConnectionState.vue";

const message = ref('')
const chatContainer = ref(null);

function sendMessage() {
  const chat = {
    owner: id.value,
    message: message.value
  }
  chatMessages.value.push(chat)
  socket.timeout(5000).emit('chat', chat)

  message.value = "";
  // 스크롤을 새 메시지 아래로 이동시킵니다.
  nextTick(() => {
    scrollChatToBottom();
  });
}

// function adjustTextarea() {
// }

function scrollChatToBottom() {
  if (chatContainer.value) {
    chatContainer.value.scrollTop = chatContainer.value.scrollHeight;
  }
}

watchEffect(()=>{
  scrollChatToBottom();
  console.log(chatMessages.value)
})

</script>

<template>
  <ConnectionState/>
  <div>
    <div class="chat-container">
      <div class="chat-messages" ref="chatContainer">
        <div v-for="(msg, index) in chatMessages" :key="index"
             :class="msg.owner === id.toString()? 'my-chat': 'their-chat'">
          <div class="message">{{ msg.message }}</div>
        </div>
      </div>
      <textarea
          style="resize: none"
          v-model="message"
          class="chat-input"
          placeholder="메시지 입력"
          @keydown.enter="sendMessage"
      />
    </div>
  </div>

</template>
