<script setup>
import { onBeforeMount, onUnmounted, ref } from 'vue';
import { FireStates } from '@/constants/fireStates';
import { configuration } from '@/config/configuration';

// Initialize forest fire simulation
const burningForest = ref([])
onBeforeMount(async () => await initBurningForestSimulation())

// Step by step forest fire simulation
let timerId = setInterval(async () => await forestFireSimulation(), configuration.simulationDelay);
onUnmounted(() => {
    clearInterval(timerId);
});

async function initBurningForestSimulation() {
    try {
        const response = await fetch(`http://localhost:${configuration.serverPort}/forest/simulation/initialize`)
        const json = await response.json()
        fillBurningForest(json)
    } catch(error) {
        console.error(error.message)
    }
}

async function forestFireSimulation() {
    try {
        const response = await fetch(`http://localhost:${configuration.serverPort}/forest/simulation/advance`)
        const json = await response.json()
        fillBurningForest(json)
    } catch(error) {
        console.error(error.message)
    }
}

function fillBurningForest(json) {
    let isFire = false;
    json.forEach((row, i) => {
        burningForest.value.push([])
        row.forEach((column, j) => {
            if(column == FireStates.Fire) isFire = true
            burningForest.value[i][j] = column
        })
    })
    if(!isFire) clearInterval(timerId)
}

function getClass(currentCell) {
    switch(currentCell) {
        case FireStates.NoFire:
            return 'no-fire'
        case FireStates.Fire:
            return 'fire'
        case FireStates.Ashes:
            return 'ashes'
    }
}
</script>

<template>
    <div class="simulation-container">
        <h1 class="title">Simulation of forest fire spread</h1>
        <div class="simulation-grid-container">
            <div 
                class="square-container" 
                :style="{'grid-template-columns': `repeat(${burningForest[0].length},1fr)`}" 
                v-for="(line, index) in burningForest" :key="index"
            >
                <div class="square" v-for="(cell, index) in line" :key="index">
                    <div class="cell" :class="getClass(cell)"></div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
    .simulation-container{
        display: flex;
        flex-direction:column;
        align-items: center;
        padding: 0 50px 0 50px;
    }

    .title{
        margin-bottom: 50px;
        font-size: 40px;
    }

   .square-container {
        display: grid;
    }

    .square {
        border: 1px solid;
        aspect-ratio:1/1;
    }

    .simulation-grid-container{
        width: 100%;
    }

    .cell{
        height: 100%;
        width: 100%;
    }

    .no-fire{
        background-color: #244632;
    }
    .fire{
        background-color: #c62502;
    }
    .ashes{
        background-color: #505452;
    }
</style>