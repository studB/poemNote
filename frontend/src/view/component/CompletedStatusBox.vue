<template>
  <div class="completed-status-box">
      <div class="completed-status-label">
            <span>
                <input id="level-1" type="radio" v-model="selectedCompletedStatus" value="초고본">
                <label for="level-1">초고본</label>
            </span>
            <span>
                <input id="level-2" type="radio" v-model="selectedCompletedStatus" value="수정본">
                <label for="level-2">수정본</label>
            </span>
            <span>
                <input id="level-3" type="radio" v-model="selectedCompletedStatus" value="습작본">
                <label for="level-3">습작본</label>
            </span>
            <span>
                <input id="level-4" type="radio" v-model="selectedCompletedStatus" value="걸작본">
                <label for="level-4">걸작본</label>
            </span>
        </div>
        <div class="completed-status-bar">
            <div>
                <a id="completed-status-progress"></a>
            </div>
        </div>
  </div>
</template>

<script>
export default {
    data(){
        return {
            selectedCompletedStatus: '초고본',
        }
    },
    methods: {
        setCompletedStatus(status) {
            this.selectedCompletedStatus = status;
        }
    },
    watch: {
        selectedCompletedStatus: function(newVal) {
            const status = newVal;
            const bar = document.getElementById('completed-status-progress');
            switch(status) {
                case '초고본':
                    bar.style.width = "0%";
                    break;
                case '수정본':
                    bar.style.width = "33%";
                    bar.style.borderRadius = "15px 0px 0px 15px";
                    break;
                case '습작본':
                    bar.style.width = "66%";
                    bar.style.borderRadius = "15px 0px 0px 15px";
                    break;
                case '걸작본':
                    bar.style.width = "100%";
                    bar.style.borderRadius = "15px 15px 15px 15px";
                    break;
            }
            this.$parent.changeCompletedStatus(status);
        }
    }

}
</script>

<style>

.completed-status-box {
    width: 360px;
    height: 100px;
    display: flex;
    flex-direction: column;
    justify-content: flex-end;
    align-items: center;
}

.completed-status-label {
    width: inherit;
    display: flex;
    justify-content: space-between;
}
.completed-status-label > span {
    position: relative;
    flex: 1;
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
}
.completed-status-label > span > input[type="radio"] {
    position: absolute;
    visibility: hidden;
}
.completed-status-label > span > input[type="radio"] + label {
    color: #666;
    font-size: 17px;
    cursor: pointer;
}
.completed-status-label > span > input[type="radio"]:checked + label {
    color: rgb(139, 0, 139, 1);
}

.completed-status-bar {
    padding: 0 50px;
    width: 360px;
    height: 20px;
    display: flex;
    flex-direction: row;
    align-items: flex-end;
}
.completed-status-bar > div {
    width: 270px;
    height: 10px;
    border-radius: 15px;
    background: #fff;
    box-shadow: inset 0 1px 6px 0 rgba(32, 33, 36, 0.28);
    display: flex;
    flex-direction: row;
}
.completed-status-bar > div > a {
    background: rgb(139, 0, 139, 0.5);
    width: 0%;
    border-radius: 15px 0 0 15px;
    transition: all 0.4s ease-in-out;
}

</style>