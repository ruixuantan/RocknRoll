import React, { useState } from "react";
import { IRoller } from "../.././models/IRoller";
import { Button, Container, Divider, Header, Table } from 'semantic-ui-react';

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
                            <Table.Cell>{results[0] !== undefined ? results[0].die : null}</Table.Cell>
                            <Table.Cell>{results[0] !== undefined ? results[0].result : null}</Table.Cell>
                        </Table.Row>
                        <Table.Row>
                            <Table.Cell>2</Table.Cell>
                            <Table.Cell>{results[1] !== undefined ? results[1].die : null}</Table.Cell>
                            <Table.Cell>{results[1] !== undefined ? results[1].result : null}</Table.Cell>
                        </Table.Row>
                        <Table.Row>
                            <Table.Cell>3</Table.Cell>
                            <Table.Cell>{results[2] !== undefined ? results[2].die : null}</Table.Cell>
                            <Table.Cell>{results[2] !== undefined ? results[2].result : null}</Table.Cell>
                        </Table.Row>
                        <Table.Row>
                            <Table.Cell>4</Table.Cell>
                            <Table.Cell>{results[3] !== undefined ? results[3].die : null}</Table.Cell>
                            <Table.Cell>{results[3] !== undefined ? results[3].result : null}</Table.Cell>
                        </Table.Row>
                        <Table.Row>
                            <Table.Cell>5</Table.Cell>
                            <Table.Cell>{results[4] !== undefined ? results[4].die : null}</Table.Cell>
                            <Table.Cell>{results[4] !== undefined ? results[4].result : null}</Table.Cell>
                        </Table.Row>
                    </Table.Body>
                </Table>
            </Container>
        </div>
    );
}

export default Roller;