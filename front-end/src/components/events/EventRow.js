import React from "react";
import Moment from 'moment';

export default function EventRow(props) {
    return (
        <tr>
            <td>{Moment(props.event.created).format('DD/MM/YYYY, HH:mm:ss')}</td>
            <td>{props.event.message}</td>
        </tr>
    );
}