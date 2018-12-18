package com.yada.ssp.apiServer.view;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Query extends TrxInfo {
}
