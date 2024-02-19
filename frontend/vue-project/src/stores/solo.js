
import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { useRouter } from 'vue-router';
import axios from 'axios';
import { authorizationAPI } from './authAPI';
import { serverURL, v1_URL } from './config';


export const useSoloStore = defineStore('solo', () => {
    const URL = serverURL + v1_URL + 'solo';
    // 솔로 모드 내역(오늘) 데이터 저장
    // api 연결되면 지우자.
    const soloTodayData = ref([
        {
            solo_status: 1,
            solo_result: 1,
            category_id: 1,
        },
        {
            solo_status: 0,
            solo_result: 1,
            category_id: 2,
        },
        {
            solo_status: 1,
            solo_result: 1,
            category_id: 3,
        },
        {
            solo_status: 1,
            solo_result: 1,
            category_id: 4,
        },
        {
            solo_status: 0,
            solo_result: 1,
            category_id: 5,
        },
        {
            solo_status: 0,
            solo_result: 1,
            category_id: 6,
        },
    ])

    // 솔로 모드 내역 표시(오늘) 함수
    // 한번 테스트 해봐야함
    const soloToday = function (userId) {
        return new Promise((resolve, reject) => {
            authorizationAPI
                .get(`${URL}/today-list/${userId}`)
                .then((res) => {
                    resolve(res);
                })
                .catch((err) => {
                    console.log(err);
                    reject(err);
                });
        });
    }

    //솔로모드 도전
    const soloChallenge = function (challengeData) {

        const solo_auth = {
            "userId": challengeData.user_id,
            "categoryId": challengeData.category_id,
            "soloAuthImg": "",
        }

        return new Promise((resolve, reject) => {
            authorizationAPI
                .post(`${URL}/challenge`, solo_auth)
                .then((res) => {
                    resolve(res);
                })
                .catch((err) => {
                    console.log(err);
                    reject(err)
                    console.log(solo_auth);
                })
        })
    }

    //솔로모드 인증
    const soloAuth = function (formData) {
        // console.log('axios');
        // for (let [key, value] of formData.entries()) {
        //     console.log(`${key}: ${value}`);
        // }
        return new Promise((resolve, reject) => {
            authorizationAPI.request({
                method: 'patch', 
                url: `${URL}/challenge-auth`, 
                data: formData, 
                headers: { 'Content-Type': 'multipart/form-data' } 
            })
                .then(res => {
                    resolve(res);
                    // console.log('업로드 완료');
                })
                .catch(err => {
                    console.log(err);
                    reject(err);
                });
        });
    }

    //솔로모드 내역(전체 리스트)
    const soloList = function (userId) {
        return new Promise((resolve, reject) => {
            authorizationAPI
                .get(`${URL}/challenge-list/${userId}`)
                .then((res) => {
                    resolve(res);
                })
                .catch((err) => {
                    console.log(err);
                    reject(err);
                });
        });
    }
    return { soloTodayData, soloToday, soloChallenge, soloAuth, soloList }
})



