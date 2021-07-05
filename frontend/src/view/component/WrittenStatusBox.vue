<template>
  <div class="written-status-box">
        <div class="written-status-label">
            <span>
                <input id="level-1" type="radio" v-model="selectedWrittenStatus" value="씨앗">
                <label for="level-1">씨앗</label>
            </span>
            <span>
                <input id="level-2" type="radio" v-model="selectedWrittenStatus" value="작품미달">
                <label for="level-2">작품미달</label>
            </span>
            <span>
                <input id="level-3" type="radio" v-model="selectedWrittenStatus" value="발행가능">
                <label for="level-3">발행가능</label>
            </span>
        </div>
        <div class="written-status-bar">
            <div>
                <a id="written-status-progress"></a>
            </div>
        </div>
  </div>
</template>

<script>
export default {
    data() {
        return {
            selectedWrittenStatus: '씨앗',
        }
    },
    methods: {
        refreshWrittenStatus() {
            this.selectedWrittenStatus = '씨앗';
        },
        setWrittenStatus(status) {
            this.selectedWrittenStatus = status;
        }
    },
    watch: {
        selectedWrittenStatus: function(newVal) {
            const status = newVal;
            const bar = document.getElementById('written-status-progress');
            switch(status) {
                case '씨앗':
                    bar.style.width = "0%";
                    break;
                case '작품미달':
                    bar.style.width = "50%";
                    bar.style.borderRadius = "15px 0px 0px 15px";
                    break;
                case '발행가능':
                    bar.style.width = "100%";
                    bar.style.borderRadius = "15px 15px 15px 15px";
                    break;    
            }
            this.$parent.changeWrittenStatus(status);
        }
    }
}
</script>

<style>

.written-status-box {
    width: 300px;
    height: 100px;
    display: flex;
    flex-direction: column;
    justify-content: flex-end;
    align-items: center;
}

.written-status-label {
    width: inherit;
    display: flex;
    justify-content: space-between;
}
.written-status-label > span {
    position: relative;
    flex: 1;
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
}
.written-status-label > span > input[type="radio"] {
    position: absolute;
    visibility: hidden;
}
.written-status-label > span > input[type="radio"] + label {
    color: #666;
    font-size: 17px;
    cursor: pointer;
}
.written-status-label > span > input[type="radio"]:checked + label {
    color: rgba(0, 76, 3, 1);
}

.written-status-bar {
    padding: 0 50px;
    width: 300px;
    height: 20px;
    display: flex;
    flex-direction: row;
    align-items: flex-end;
}
.written-status-bar > div {
    width: 200px;
    height: 10px;
    border-radius: 15px;
    background: #fff;
    box-shadow: inset 0 1px 6px 0 rgba(32, 33, 36, 0.28);
    display: flex;
    flex-direction: row;
}
.written-status-bar > div > a {
    background: rgb(0, 76, 3, 0.5);
    width: 0%;
    border-radius: 15px 0 0 15px;
    transition: all 0.4s ease-in-out;
}


</style>