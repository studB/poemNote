<template>
  <div class="value-status-box">
        <span>
            <input id="value-1" type="radio" v-model="selectedValueStatus" value="가">
            <label for="value-1">가</label>
        </span>
        <span>
            <input id="value-2" type="radio" v-model="selectedValueStatus" value="양">
            <label for="value-2">양</label>
        </span>
        <span>
            <input id="value-3" type="radio" v-model="selectedValueStatus" value="미">
            <label for="value-3">미</label>
        </span>
        <span>
            <input id="value-4" type="radio" v-model="selectedValueStatus" value="우">
            <label for="value-4">우</label>
        </span>
        <span>
            <input id="value-5" type="radio" v-model="selectedValueStatus" value="수">
            <label for="value-5">수</label>
        </span>
  </div>
</template>

<script>
export default {
    data() {
        return {
            selectedValueStatus: '가'
        }
    },
    methods: {
        setValueStatus(status) {
            this.selectedValueStatus = status;
        }
    },
    watch: {
        selectedValueStatus: function(newVal){
            this.$parent.changeValueStatus(newVal);
        }
    }
}
</script>

<style>

.value-status-box {
    margin-top: 10px;
    width: 360px;
    height: 70px;
    display: flex;
    justify-content: space-between;
    align-items: flex-end;
}
.value-status-box > span > input[type="radio"] {
    position: absolute;
    visibility: hidden;
}
.value-status-box > span > input[type="radio"] + label {
    width: 30px;
    height: 30px;
    border-radius: 50%;
    border: none;
    box-shadow: 0 1px 2px 1px rgba(32, 33, 38, 0.8);
    color: #666;
    font-size: 17px;
    cursor: pointer;
    display: flex;
    justify-content: center;
    align-items: center;
}
.value-status-box > span > input[type="radio"]:checked + label {
    box-shadow: 0 1px 2px 3px rgba(139, 0, 139, 0.8);
    color: rgba(139, 0, 139);
}

</style>