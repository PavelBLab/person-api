
import http from 'k6/http';
import { check, sleep } from 'k6';
import { Rate } from 'k6/metrics';

export let errorRate = new Rate('errors');

export let options = {
    stages: [
        { duration: '1m', target: 1000 }, // Ramp-up to 1000 CCU over 1 minute
        { duration: '5m', target: 1000 }, // Stay at 1000 CCU for 5 minutes
        { duration: '1m', target: 0 }, // Ramp-down to 0 CCU over 1 minute
    ],
};

export default function () {
    let res = http.get('http://localhost:18080/persons');
    check(res, {
        'status is 200': (r) => r.status === 200,
    }) || errorRate.add(1);

    sleep(1); // Simulate user think time
}

