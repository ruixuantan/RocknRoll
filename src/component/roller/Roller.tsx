import React, { useState } from "react";
import { RollRow } from "../.././models/RollRow";
import { Button, Container, Divider, Header, Table } from 'semantic-ui-react';

const roll = (side: number): number => {
    return Math.floor(Math.random() * side) + 1;
}

interface IDieCell {
    rollRow: RollRow
}

const DieCell: React.FC<IDieCell> = ({rollRow}) => {
    return <Table.Cell>{rollRow !== undefined ? rollRow.die : null}</Table.Cell>;
}

interface IResultCell {
    rollRow: RollRow
}

const ResultCell: React.FC<IResultCell> = ({rollRow}) => {
    return <Table.Cell>{rollRow !== undefined ? rollRow.result : null}</Table.Cell>;
}

const Roller: React.FC = () => {
    const [results, setResult] = useState<Array<RollRow>>([]);

    //some sort of a queue
    const updateResult = (die: string, roll: number): void => {
        setResult(prev => {
            const toAdd: RollRow = { "die": die, "result": roll };
            if (prev.length >= 5) {
                prev.shift();
            }
            prev = [...prev, toAdd];
            return prev;
        });
    }

    return (
        <div>
            <Header as='h2' textAlign='center'>
                Roller
            </Header>

            <Container>
                <Button size='tiny' onClick={() => updateResult("d4", roll(4))}>
                    Roll d4
                </Button>
                <Button size='tiny' onClick={() => updateResult("d6", roll(6))}>
                    Roll d6
                </Button>
                <Button size='tiny' onClick={() => updateResult("d8", roll(8))}>
                    Roll d8
                </Button>
                <Button size='tiny' onClick={() => updateResult("d10", roll(10))}>
                    Roll d10
                </Button>
                <Button size='tiny' onClick={() => updateResult("d12", roll(12))}>
                    Roll d12
                </Button>
                <Button size='tiny' onClick={() => updateResult("d20", roll(20))}>
                    Roll d20
                </Button>
                <Button size='tiny' onClick={() => updateResult("d100", roll(100))}>
                    Roll d100
                </Button>
            </Container>

            <Divider horizontal></Divider>

            <Container>
                <Table celled fixed unstackable singleLine size='small'>
                    <Table.Header>
                        <Table.HeaderCell>Index</Table.HeaderCell>
                        <Table.HeaderCell>Die</Table.HeaderCell>
                        <Table.HeaderCell>Result</Table.HeaderCell>
                    </Table.Header>

                    <Table.Body>
                        <Table.Row>
                            <Table.Cell>1</Table.Cell>
                            <DieCell rollRow={results[0]}/>
                            <ResultCell rollRow={results[0]}/>
                        </Table.Row>
                        <Table.Row>
                            <Table.Cell>2</Table.Cell>
                            <DieCell rollRow={results[1]}/>
                            <ResultCell rollRow={results[1]}/>
                        </Table.Row>
                        <Table.Row>
                            <Table.Cell>3</Table.Cell>
                            <DieCell rollRow={results[2]}/>
                            <ResultCell rollRow={results[2]}/>
                        </Table.Row>
                        <Table.Row>
                            <Table.Cell>4</Table.Cell>
                            <DieCell rollRow={results[3]}/>
                            <ResultCell rollRow={results[3]}/>
                        </Table.Row>
                        <Table.Row>
                            <Table.Cell>5</Table.Cell>
                            <DieCell rollRow={results[4]}/>
                            <ResultCell rollRow={results[4]}/>
                        </Table.Row>
                    </Table.Body>
                </Table>
            </Container>
        </div>
    );
}

export default Roller;