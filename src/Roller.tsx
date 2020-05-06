import React, { useState } from "react";
import { IRoller } from "./models/IRoller";

const roll = (side: number): number => {
    return Math.floor(Math.random() * side) + 1;
}

const Roller: React.FC = () => {
    const [results, setResult] = useState<Array<IRoller>>([]);

    //some sort of a queue
    const updateResult = (die: string, roll: number): void => {
        setResult(prev => {
            const toAdd: IRoller = { "die": die, "result": roll };
            if (prev.length >= 5) {
                prev.shift();
            }
            prev = [...prev, toAdd];
            return prev;
        });
    }

    return (
        <div>
            <h2>Roller</h2>
            <button onClick = {() => updateResult("d4", roll(4))}>
                Roll d4
            </button>
            <button onClick = {() => updateResult("d6", roll(6))}>
                Roll d6
            </button>
            <button onClick = {() => updateResult("d8", roll(8))}>
                Roll d8
            </button>
            <button onClick = {() => updateResult("d10", roll(10))}>
                Roll d10
            </button>
            <button onClick = {() => updateResult("d20", roll(20))}>
                Roll d20
            </button>
            <button onClick = {() => updateResult("d100", roll(100))}>
                Roll d100
            </button>
            <div className = "Results">
                <table>
                    <tr>
                        <th>Index</th>
                        <th>Die</th>
                        <th>Result</th>
                    </tr>
                    <tr>
                        <td>1</td>
                        <td>{results[0] != undefined ? results[0].die : null}</td>
                        <td>{results[0] != undefined ? results[0].result : null}</td>
                    </tr>
                    <tr>
                        <td>2</td>
                        <td>{results[1] != undefined ? results[1].die : null}</td>
                        <td>{results[1] != undefined ? results[1].result : null}</td>
                    </tr>
                    <tr>
                        <td>3</td>
                        <td>{results[2] != undefined ? results[2].die : null}</td>
                        <td>{results[2] != undefined ? results[2].result : null}</td>
                    </tr>
                    <tr>
                        <td>4</td>
                        <td>{results[3] != undefined ? results[3].die : null}</td>
                        <td>{results[3] != undefined ? results[3].result : null}</td>
                    </tr>
                    <tr>
                        <td>5</td>
                        <td>{results[4] != undefined ? results[4].die : null}</td>
                        <td>{results[4] != undefined ? results[4].result : null}</td>
                    </tr>
                </table>
            </div>
        </div>
    );
}

export default Roller;