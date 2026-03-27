package com.example.macchanger
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.sp

@Composable
fun Body(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
                .requiredWidth(width = 390.dp)
                .background(color = Color(0xff0b0f13))
        ) {
        Box(
            modifier = Modifier
                        .fillMaxWidth()
                        .requiredHeight(height = 1865.dp)
            ) {
            Column(
                modifier = Modifier
                                .requiredWidth(width = 390.dp)
                                .padding(bottom = 96.dp)
                ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                                        .fillMaxWidth()
                                        .requiredHeight(height = 64.dp)
                                        .background(color = Color(0xff0b0f13))
                                        .padding(horizontal = 24.dp)
                                        .shadow(elevation = 8.dp)
                    ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start),
                        verticalAlignment = Alignment.CenterVertically
                        ) {
                        Image(
                            painter = painterResource(id = R.drawable.container),
                            contentDescription = "Container",
                            colorFilter = ColorFilter.tint(Color(0xff00d1ff)))
                        Column(
                            modifier = Modifier
                                                        .padding(end = 52.7400016784668.dp)
                            ) {
                            Text(
                                text = "OBSIDIAN\nCOMMAND",
                                color = Color(0xff00d1ff),
                                lineHeight = 1.4.em,
                                style = TextStyle(
                                                                fontSize = 20.sp,
                                                                fontWeight = FontWeight.Bold,
                                                                letterSpacing = (-1).sp),
                                modifier = Modifier
                                                                .requiredWidth(width = 94.dp)
                                                                .requiredHeight(height = 56.dp)
                                                                .wrapContentHeight(align = Alignment.CenterVertically))
                            }
                        }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
                        verticalAlignment = Alignment.CenterVertically
                        ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                                        .clip(shape = MaterialTheme.shapes.small)
                                                        .padding(all = 8.dp)
                            ) {
                            Image(
                                painter = painterResource(id = R.drawable.container),
                                contentDescription = "Container",
                                colorFilter = ColorFilter.tint(Color(0xffa8abb1)))
                            }
                        Divider(
                            color = Color(0xff44484d).copy(alpha = 0.2f),
                            modifier = Modifier
                                                        .requiredWidth(width = 1.dp)
                                                        .requiredHeight(height = 24.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                            verticalAlignment = Alignment.CenterVertically
                            ) {
                            Box(
                                modifier = Modifier
                                                                .requiredSize(size = 8.dp)
                                                                .clip(shape = RoundedCornerShape(12.dp))
                                                                .background(color = Color(0xff8eff71))
                                                                .shadow(elevation = 8.dp,
                                                                                                shape = RoundedCornerShape(12.dp)))
                            Column() {
                                Text(
                                    text = "ENCRYPTED",
                                    color = Color(0xffa8abb1),
                                    lineHeight = 1.5.em,
                                    style = TextStyle(
                                                                        fontSize = 10.sp,
                                                                        fontWeight = FontWeight.Bold,
                                                                        letterSpacing = 1.sp),
                                    modifier = Modifier
                                                                        .requiredWidth(width = 70.dp)
                                                                        .requiredHeight(height = 15.dp)
                                                                        .wrapContentHeight(align = Alignment.CenterVertically))
                                }
                            }
                        }
                    }
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.Top),
                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(all = 16.dp)
                    ) {
                    item {
                        Box(
                            modifier = Modifier
                                                        .fillMaxWidth()
                                                        .requiredHeight(height = 236.dp)
                            ) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Color(0xff151a1f),
                                border = BorderStroke(1.dp, Color(0xff44484d).copy(alpha = 0.1f)),
                                modifier = Modifier
                                                                .clip(shape = RoundedCornerShape(8.dp))
                                ) {
                                Box(
                                    modifier = Modifier
                                                                        .requiredWidth(width = 171.dp)
                                                                        .requiredHeight(height = 97.dp)
                                    ) {
                                    Box(
                                        modifier = Modifier
                                                                                .padding(all = 20.dp)
                                        ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.container),
                                            contentDescription = "Container",
                                            colorFilter = ColorFilter.tint(Color(0xff69daff)),
                                            modifier = Modifier
                                                                                        .padding(horizontal = 49.5.dp))
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            modifier = Modifier
                                                                                        .padding(start = 14.979999542236328.dp,
                                                                                                                                    end = 14.989999771118164.dp)
                                            ) {
                                            Text(
                                                text = "SCAN LOCATION",
                                                color = Color(0xffebeef4),
                                                textAlign = TextAlign.Center,
                                                lineHeight = 1.43.em,
                                                style = TextStyle(
                                                                                                fontSize = 14.sp,
                                                                                                fontWeight = FontWeight.Bold,
                                                                                                letterSpacing = (-0.35).sp),
                                                modifier = Modifier
                                                                                                .requiredWidth(width = 99.dp)
                                                                                                .requiredHeight(height = 20.dp)
                                                                                                .wrapContentHeight(align = Alignment.CenterVertically))
                                            }
                                        }
                                    Box(
                                        modifier = Modifier
                                                                                .fillMaxSize()
                                                                                .background(brush = Brush.linearGradient(
                    0f to Color(0xff69daff).copy(alpha = 0.05f), 
1f to Color(0xff69daff),
                    start = Offset(35.5f, -19.95f),
                    end = Offset(133.5f, 114.95f))))
                                    }
                                }
                            Column(
                                verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
                                modifier = Modifier
                                                                .clip(shape = RoundedCornerShape(8.dp))
                                                                .background(color = Color(0xff151a1f))
                                                                .border(border = BorderStroke(1.dp, Color(0xff44484d).copy(alpha = 0.1f)),
                                                                                                shape = RoundedCornerShape(8.dp))
                                                                .padding(all = 20.dp)
                                ) {
                                Image(
                                    painter = painterResource(id = R.drawable.container),
                                    contentDescription = "Container",
                                    colorFilter = ColorFilter.tint(Color(0xff69daff)),
                                    modifier = Modifier
                                                                        .padding(horizontal = 49.5.dp))
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                                                        .padding(start = 17.049999237060547.dp,
                                                                                                            end = 17.040000915527344.dp)
                                    ) {
                                    Text(
                                        text = "DETECT DEVICE",
                                        color = Color(0xffebeef4),
                                        textAlign = TextAlign.Center,
                                        lineHeight = 1.43.em,
                                        style = TextStyle(
                                                                                fontSize = 14.sp,
                                                                                fontWeight = FontWeight.Bold,
                                                                                letterSpacing = (-0.35).sp),
                                        modifier = Modifier
                                                                                .requiredWidth(width = 95.dp)
                                                                                .requiredHeight(height = 20.dp)
                                                                                .wrapContentHeight(align = Alignment.CenterVertically))
                                    }
                                }
                            Column(
                                verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
                                modifier = Modifier
                                                                .clip(shape = RoundedCornerShape(8.dp))
                                                                .background(color = Color(0xff1b2026))
                                                                .border(border = BorderStroke(1.dp, Color(0xff69daff).copy(alpha = 0.2f)),
                                                                                                shape = RoundedCornerShape(8.dp))
                                                                .padding(all = 20.dp)
                                                                .shadow(elevation = 15.dp,
                                                                                                shape = RoundedCornerShape(8.dp))
                                ) {
                                Image(
                                    painter = painterResource(id = R.drawable.container),
                                    contentDescription = "Container",
                                    colorFilter = ColorFilter.tint(Color(0xff69daff)),
                                    modifier = Modifier
                                                                        .padding(horizontal = 49.5.dp))
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                                                        .padding(start = 25.780000686645508.dp,
                                                                                                            end = 25.799999237060547.dp)
                                    ) {
                                    Text(
                                        text = "BACKUP EFS",
                                        color = Color(0xffebeef4),
                                        textAlign = TextAlign.Center,
                                        lineHeight = 1.43.em,
                                        style = TextStyle(
                                                                                fontSize = 14.sp,
                                                                                fontWeight = FontWeight.Bold,
                                                                                letterSpacing = (-0.35).sp),
                                        modifier = Modifier
                                                                                .requiredWidth(width = 77.dp)
                                                                                .requiredHeight(height = 20.dp)
                                                                                .wrapContentHeight(align = Alignment.CenterVertically))
                                    }
                                }
                            Column(
                                verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
                                modifier = Modifier
                                                                .clip(shape = RoundedCornerShape(8.dp))
                                                                .background(color = Color(0xff151a1f))
                                                                .border(border = BorderStroke(1.dp, Color(0xff44484d).copy(alpha = 0.1f)),
                                                                                                shape = RoundedCornerShape(8.dp))
                                                                .padding(all = 20.dp)
                                ) {
                                Image(
                                    painter = painterResource(id = R.drawable.container),
                                    contentDescription = "Container",
                                    colorFilter = ColorFilter.tint(Color(0xff69daff)),
                                    modifier = Modifier
                                                                        .padding(horizontal = 49.5.dp))
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                                                        .padding(horizontal = 9.dp)
                                    ) {
                                    Text(
                                        text = "RESTORE BACKUP",
                                        color = Color(0xffebeef4),
                                        textAlign = TextAlign.Center,
                                        lineHeight = 1.43.em,
                                        style = TextStyle(
                                                                                fontSize = 14.sp,
                                                                                fontWeight = FontWeight.Bold,
                                                                                letterSpacing = (-0.35).sp),
                                        modifier = Modifier
                                                                                .requiredWidth(width = 111.dp)
                                                                                .requiredHeight(height = 20.dp)
                                                                                .wrapContentHeight(align = Alignment.CenterVertically))
                                    }
                                }
                            }
                        }
                    item {
                        Box(
                            modifier = Modifier
                                                        .fillMaxWidth()
                                                        .requiredHeight(height = 637.dp)
                            ) {
                            Surface(
                                shape = RoundedCornerShape(16.dp),
                                color = Color(0xff151a1f),
                                border = BorderStroke(1.dp, Color(0xff44484d).copy(alpha = 0.05f)),
                                modifier = Modifier
                                                                .clip(shape = RoundedCornerShape(16.dp))
                                                                .shadow(elevation = 10.dp,
                                                                                                shape = RoundedCornerShape(16.dp))
                                ) {
                                Box(
                                    modifier = Modifier
                                                                        .requiredWidth(width = 358.dp)
                                                                        .requiredHeight(height = 310.dp)
                                                                        .shadow(elevation = 10.dp)
                                    ) {
                                    Box(
                                        modifier = Modifier
                                                                                .requiredWidth(width = 358.dp)
                                                                                .padding(all = 24.dp)
                                        ) {
                                        Row(
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier
                                                                                        .fillMaxWidth()
                                            ) {
                                            Column(
                                                modifier = Modifier
                                                                                                .padding(end = 102.69000244140625.dp)
                                                ) {
                                                Text(
                                                    text = "INTERFACE\nCONTROL",
                                                    color = Color(0xff00d1ff),
                                                    lineHeight = 1.4.em,
                                                    style = TextStyle(
                                                                                                        fontSize = 20.sp,
                                                                                                        fontWeight = FontWeight.Bold,
                                                                                                        letterSpacing = 2.sp),
                                                    modifier = Modifier
                                                                                                        .requiredWidth(width = 118.dp)
                                                                                                        .requiredHeight(height = 56.dp)
                                                                                                        .wrapContentHeight(align = Alignment.CenterVertically))
                                                }
                                            Column(
                                                modifier = Modifier
                                                                                                .clip(shape = RoundedCornerShape(6.dp))
                                                                                                .background(color = Color(0xff21262c))
                                                                                                .padding(start = 8.dp,
                                                                                                                                                end = 41.36000061035156.dp,
                                                                                                                                                top = 4.dp,
                                                                                                                                                bottom = 4.dp)
                                                ) {
                                                Text(
                                                    text = "ETH0 /\nACTIVE",
                                                    color = Color(0xffa8abb1),
                                                    lineHeight = 1.5.em,
                                                    style = TextStyle(
                                                                                                        fontSize = 10.sp,
                                                                                                        fontWeight = FontWeight.Bold),
                                                    modifier = Modifier
                                                                                                        .requiredWidth(width = 37.dp)
                                                                                                        .requiredHeight(height = 30.dp)
                                                                                                        .wrapContentHeight(align = Alignment.CenterVertically))
                                                }
                                            }
                                        Column(
                                            verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.Top),
                                            modifier = Modifier
                                                                                        .fillMaxWidth()
                                            ) {
                                            Box(
                                                modifier = Modifier
                                                                                                .fillMaxWidth()
                                                                                                .requiredHeight(height = 88.dp)
                                                ) {
                                                Column(
                                                    modifier = Modifier
                                                                                                        .requiredWidth(width = 308.dp)
                                                    ) {
                                                    Row(
                                                        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start),
                                                        modifier = Modifier
                                                                                                                .fillMaxWidth()
                                                        ) {
                                                        Box(
                                                            modifier = Modifier
                                                                                                                        .fillMaxSize()
                                                                                                                        .weight(weight = 1f)
                                                            ) {
                                                            Column(
                                                                modifier = Modifier
                                                                                                                                .requiredWidth(width = 191.dp)
                                                                                                                                .requiredHeight(height = 88.dp)
                                                                ) {
                                                                Row(
                                                                    horizontalArrangement = Arrangement.Center,
                                                                    modifier = Modifier
                                                                                                                                        .fillMaxWidth()
                                                                                                                                        .clip(shape = RoundedCornerShape(8.dp))
                                                                                                                                        .background(color = Color(0xff21262c))
                                                                                                                                        .padding(horizontal = 20.dp,
                                                                                                                                                                                                            vertical = 16.dp)
                                                                    ) {
                                                                    Column(
                                                                        modifier = Modifier
                                                                                                                                                .fillMaxWidth()
                                                                        ) {
                                                                        Text(
                                                                            text = "00:1A:2B:3C:4D:5E",
                                                                            color = Color(0xff69daff),
                                                                            lineHeight = 1.56.em,
                                                                            style = TextStyle(
                                                                                                                                                        fontSize = 18.sp,
                                                                                                                                                        letterSpacing = 1.8.sp),
                                                                            modifier = Modifier
                                                                                                                                                        .fillMaxWidth()
                                                                                                                                                        .wrapContentHeight(align = Alignment.CenterVertically))
                                                                        }
                                                                    }
                                                                }
                                                            Row(
                                                                modifier = Modifier
                                                                                                                                .align(alignment = Alignment.TopEnd)
                                                                                                                                .offset(x = (-15.99).dp,
                                                                                                                                                                                                y = 0.dp)
                                                                                                                                .fillMaxHeight()
                                                                ) {
                                                                Image(
                                                                    painter = painterResource(id = R.drawable.container),
                                                                    contentDescription = "Container",
                                                                    colorFilter = ColorFilter.tint(Color(0xff8eff71)),
                                                                    modifier = Modifier
                                                                                                                                        .fillMaxHeight())
                                                                }
                                                            }
                                                        Column(
                                                            verticalArrangement = Arrangement.Center,
                                                            horizontalAlignment = Alignment.CenterHorizontally,
                                                            modifier = Modifier
                                                                                                                        .clip(shape = RoundedCornerShape(8.dp))
                                                                                                                        .background(color = Color(0xff21262c))
                                                                                                                        .padding(horizontal = 24.dp,
                                                                                                                                                                                    vertical = 22.dp)
                                                            ) {
                                                            Text(
                                                                text = "RANDOM",
                                                                color = Color(0xff00d1ff),
                                                                textAlign = TextAlign.Center,
                                                                lineHeight = 1.33.em,
                                                                style = TextStyle(
                                                                                                                                fontSize = 12.sp,
                                                                                                                                fontWeight = FontWeight.Bold,
                                                                                                                                letterSpacing = 1.2.sp),
                                                                modifier = Modifier
                                                                                                                                .requiredWidth(width = 57.dp)
                                                                                                                                .requiredHeight(height = 16.dp)
                                                                                                                                .wrapContentHeight(align = Alignment.CenterVertically))
                                                            }
                                                        }
                                                    }
                                                Column(
                                                    modifier = Modifier
                                                                                                        .align(alignment = Alignment.TopStart)
                                                                                                        .offset(x = 16.dp,
                                                                                                                                                            y = (-10).dp)
                                                                                                        .background(color = Color(0xff151a1f))
                                                                                                        .padding(horizontal = 8.dp)
                                                    ) {
                                                    Text(
                                                        text = "TARGET MAC ADDRESS",
                                                        color = Color(0xffa8abb1),
                                                        lineHeight = 1.5.em,
                                                        style = TextStyle(
                                                                                                                fontSize = 10.sp,
                                                                                                                fontWeight = FontWeight.Bold,
                                                                                                                letterSpacing = 1.sp),
                                                        modifier = Modifier
                                                                                                                .requiredWidth(width = 134.dp)
                                                                                                                .requiredHeight(height = 15.dp)
                                                                                                                .wrapContentHeight(align = Alignment.CenterVertically))
                                                    }
                                                }
                                            Row(
                                                horizontalArrangement = Arrangement.Center,
                                                verticalAlignment = Alignment.CenterVertically,
                                                modifier = Modifier
                                                                                                .fillMaxWidth()
                                                                                                .clip(shape = RoundedCornerShape(8.dp))
                                                                                                .background(brush = Brush.linearGradient(
                    0f to Color(0xff69daff), 
1f to Color(0xff00cffc),
                    start = Offset(0f, 34f),
                    end = Offset(308f, 34f)))
                                                                                                .padding(vertical = 20.dp)
                                                                                                .shadow(elevation = 30.dp,
                                                                                                                                                shape = RoundedCornerShape(8.dp))
                                                ) {
                                                Text(
                                                    text = "CHANGE MAC ADDRESS",
                                                    color = Color(0xff004050),
                                                    textAlign = TextAlign.Center,
                                                    lineHeight = 1.56.em,
                                                    style = TextStyle(
                                                                                                        fontSize = 18.sp,
                                                                                                        fontWeight = FontWeight.Bold,
                                                                                                        letterSpacing = 3.6.sp),
                                                    modifier = Modifier
                                                                                                        .requiredWidth(width = 259.dp)
                                                                                                        .requiredHeight(height = 28.dp)
                                                                                                        .wrapContentHeight(align = Alignment.CenterVertically))
                                                }
                                            }
                                        }
                                    Box(
                                        modifier = Modifier
                                                                                .fillMaxSize()
                                                                                .clip(shape = RoundedCornerShape(16.dp))
                                                                                .background(color = Color.White)
                                                                                .shadow(elevation = 10.dp,
                                                                                                                        shape = RoundedCornerShape(16.dp)))
                                    }
                                }
                            Column(
                                verticalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                                                .fillMaxHeight()
                                                                .requiredWidth(width = 358.dp)
                                                                .clip(shape = RoundedCornerShape(16.dp))
                                                                .background(color = Color(0xff151a1f))
                                                                .border(border = BorderStroke(1.dp, Color(0xff44484d).copy(alpha = 0.05f)),
                                                                                                shape = RoundedCornerShape(16.dp))
                                                                .padding(all = 24.dp)
                                ) {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
                                    modifier = Modifier
                                                                        .fillMaxWidth()
                                    ) {
                                    Column(
                                        modifier = Modifier
                                                                                .fillMaxWidth()
                                        ) {
                                        Text(
                                            text = "DEVICE IDENTITY",
                                            color = Color(0xffa8abb1),
                                            lineHeight = 1.5.em,
                                            style = TextStyle(
                                                                                        fontSize = 10.sp,
                                                                                        letterSpacing = 1.sp),
                                            modifier = Modifier
                                                                                        .fillMaxWidth()
                                                                                        .wrapContentHeight(align = Alignment.CenterVertically))
                                        }
                                    Column(
                                        modifier = Modifier
                                                                                .fillMaxWidth()
                                        ) {
                                        Text(
                                            text = "HARDWARE\nSIGNATURE",
                                            color = Color(0xffebeef4),
                                            lineHeight = 1.25.em,
                                            style = TextStyle(
                                                                                        fontSize = 24.sp,
                                                                                        fontWeight = FontWeight.Bold),
                                            modifier = Modifier
                                                                                        .fillMaxWidth()
                                                                                        .wrapContentHeight(align = Alignment.CenterVertically))
                                        }
                                    }
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
                                    modifier = Modifier
                                                                        .fillMaxWidth()
                                                                        .padding(vertical = 16.dp)
                                    ) {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(189.68.dp, Alignment.Start),
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                                                                .fillMaxWidth()
                                        ) {
                                        Column() {
                                            Text(
                                                text = "VENDOR",
                                                color = Color(0xffa8abb1),
                                                lineHeight = 1.33.em,
                                                style = TextStyle(
                                                                                                fontSize = 12.sp),
                                                modifier = Modifier
                                                                                                .requiredWidth(width = 50.dp)
                                                                                                .requiredHeight(height = 16.dp)
                                                                                                .wrapContentHeight(align = Alignment.CenterVertically))
                                            }
                                        Column() {
                                            Text(
                                                text = "INTEL CORP.",
                                                color = Color(0xffebeef4),
                                                lineHeight = 1.33.em,
                                                style = TextStyle(
                                                                                                fontSize = 12.sp,
                                                                                                fontWeight = FontWeight.Bold,
                                                                                                letterSpacing = (-0.6).sp),
                                                modifier = Modifier
                                                                                                .requiredWidth(width = 68.dp)
                                                                                                .requiredHeight(height = 16.dp)
                                                                                                .wrapContentHeight(align = Alignment.CenterVertically))
                                            }
                                        }
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(215.18.dp, Alignment.Start),
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                                                                .fillMaxWidth()
                                        ) {
                                        Column() {
                                            Text(
                                                text = "UPTIME",
                                                color = Color(0xffa8abb1),
                                                lineHeight = 1.33.em,
                                                style = TextStyle(
                                                                                                fontSize = 12.sp),
                                                modifier = Modifier
                                                                                                .requiredWidth(width = 46.dp)
                                                                                                .requiredHeight(height = 16.dp)
                                                                                                .wrapContentHeight(align = Alignment.CenterVertically))
                                            }
                                        Column() {
                                            Text(
                                                text = "14:22:05",
                                                color = Color(0xff8eff71),
                                                lineHeight = 1.33.em,
                                                style = TextStyle(
                                                                                                fontSize = 12.sp,
                                                                                                fontWeight = FontWeight.Bold,
                                                                                                letterSpacing = (-0.6).sp),
                                                modifier = Modifier
                                                                                                .requiredWidth(width = 47.dp)
                                                                                                .requiredHeight(height = 16.dp)
                                                                                                .wrapContentHeight(align = Alignment.CenterVertically))
                                            }
                                        }
                                    }
                                Column(
                                    modifier = Modifier
                                                                        .fillMaxWidth()
                                                                        .clip(shape = RoundedCornerShape(8.dp))
                                                                        .background(color = Color.Black)
                                                                        .border(border = BorderStroke(1.dp, Color(0xff44484d).copy(alpha = 0.1f)),
                                                                                                            shape = RoundedCornerShape(8.dp))
                                                                        .padding(all = 12.dp)
                                    ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_icon),
                                        contentDescription = "AB6AXuCFWlW1J4l6ORVW9ks5kYeaWDDRHCxC7BEYmMqr2cqJeHTmSFXHy_BZpZZU28QaQY5hOWA5Xpkpu5ZD4QX3OgRCzHSq93GSbVuXm_3e-krHUBpjScVY6gkdmnr7wg596T2y6xgB8QwB0qn8EDPae--MpFtglCuiNgn-EzObWfE2z-mvana5AMqwxM9QOoFoAiRKzMBVboc0MM_gKyoMEIOEn3IieRuZ2bV-NHsnMzBbI8Lr3TzBpVwcycpo9boxiYTEyrvvcgsjRBo",
                                        alpha = 0.6f,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                                                                .fillMaxWidth()
                                                                                .requiredHeight(height = 96.dp)
                                                                                .clip(shape = MaterialTheme.shapes.medium))
                                    }
                                }
                            }
                        }
                    item {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
                            modifier = Modifier
                                                        .fillMaxWidth()
                            ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                                                .fillMaxWidth()
                                ) {
                                Divider(
                                    color = Color(0xff44484d).copy(alpha = 0.1f),
                                    modifier = Modifier
                                                                        .requiredHeight(height = 1.dp)
                                                                        .weight(weight = 0.5f))
                                Column() {
                                    Text(
                                        text = "EXTENDED TOOLSET",
                                        color = Color(0xffa8abb1),
                                        lineHeight = 1.33.em,
                                        style = TextStyle(
                                                                                fontSize = 12.sp,
                                                                                fontWeight = FontWeight.Bold,
                                                                                letterSpacing = 3.6.sp),
                                        modifier = Modifier
                                                                                .requiredWidth(width = 169.dp)
                                                                                .requiredHeight(height = 16.dp)
                                                                                .wrapContentHeight(align = Alignment.CenterVertically))
                                    }
                                Divider(
                                    color = Color(0xff44484d).copy(alpha = 0.1f),
                                    modifier = Modifier
                                                                        .requiredHeight(height = 1.dp)
                                                                        .weight(weight = 0.5f))
                                }
                            Box(
                                modifier = Modifier
                                                                .fillMaxWidth()
                                                                .requiredHeight(height = 352.dp)
                                ) {
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                                                        .clip(shape = RoundedCornerShape(8.dp))
                                                                        .background(color = Color(0xff0f1418))
                                                                        .padding(start = 35.20000076293945.dp,
                                                                                                            end = 35.220001220703125.dp,
                                                                                                            top = 16.dp,
                                                                                                            bottom = 16.dp)
                                    ) {
                                    Column(
                                        modifier = Modifier
                                                                                .padding(bottom = 8.dp)
                                        ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.container),
                                            contentDescription = "Container",
                                            colorFilter = ColorFilter.tint(Color(0xffa8abb1)))
                                        }
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                        Text(
                                            text = "HISTORY",
                                            color = Color(0xffa8abb1),
                                            textAlign = TextAlign.Center,
                                            lineHeight = 1.5.em,
                                            style = TextStyle(
                                                                                        fontSize = 10.sp,
                                                                                        fontWeight = FontWeight.Bold,
                                                                                        letterSpacing = (-0.5).sp),
                                            modifier = Modifier
                                                                                        .requiredWidth(width = 41.dp)
                                                                                        .requiredHeight(height = 15.dp)
                                                                                        .wrapContentHeight(align = Alignment.CenterVertically))
                                        }
                                    }
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                                                        .clip(shape = RoundedCornerShape(8.dp))
                                                                        .background(color = Color(0xff0f1418))
                                                                        .padding(start = 33.79999923706055.dp,
                                                                                                            end = 33.810001373291016.dp,
                                                                                                            top = 16.dp,
                                                                                                            bottom = 16.dp)
                                    ) {
                                    Column(
                                        modifier = Modifier
                                                                                .padding(bottom = 8.dp)
                                        ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.container),
                                            contentDescription = "Container",
                                            colorFilter = ColorFilter.tint(Color(0xffa8abb1)))
                                        }
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                        Text(
                                            text = "PROFILES",
                                            color = Color(0xffa8abb1),
                                            textAlign = TextAlign.Center,
                                            lineHeight = 1.5.em,
                                            style = TextStyle(
                                                                                        fontSize = 10.sp,
                                                                                        fontWeight = FontWeight.Bold,
                                                                                        letterSpacing = (-0.5).sp),
                                            modifier = Modifier
                                                                                        .requiredWidth(width = 44.dp)
                                                                                        .requiredHeight(height = 15.dp)
                                                                                        .wrapContentHeight(align = Alignment.CenterVertically))
                                        }
                                    }
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                                                        .clip(shape = RoundedCornerShape(8.dp))
                                                                        .background(color = Color(0xff0f1418))
                                                                        .padding(start = 34.290000915527344.dp,
                                                                                                            end = 34.29999923706055.dp,
                                                                                                            top = 16.dp,
                                                                                                            bottom = 16.dp)
                                    ) {
                                    Column(
                                        modifier = Modifier
                                                                                .padding(bottom = 8.dp)
                                        ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.container),
                                            contentDescription = "Container",
                                            colorFilter = ColorFilter.tint(Color(0xffa8abb1)))
                                        }
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                        Text(
                                            text = "NET INFO",
                                            color = Color(0xffa8abb1),
                                            textAlign = TextAlign.Center,
                                            lineHeight = 1.5.em,
                                            style = TextStyle(
                                                                                        fontSize = 10.sp,
                                                                                        fontWeight = FontWeight.Bold,
                                                                                        letterSpacing = (-0.5).sp),
                                            modifier = Modifier
                                                                                        .requiredWidth(width = 43.dp)
                                                                                        .requiredHeight(height = 15.dp)
                                                                                        .wrapContentHeight(align = Alignment.CenterVertically))
                                        }
                                    }
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                                                        .clip(shape = RoundedCornerShape(8.dp))
                                                                        .background(color = Color(0xff0f1418))
                                                                        .padding(horizontal = 30.780000686645508.dp,
                                                                                                            vertical = 16.dp)
                                    ) {
                                    Column(
                                        modifier = Modifier
                                                                                .padding(bottom = 8.dp)
                                        ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.container),
                                            contentDescription = "Container",
                                            colorFilter = ColorFilter.tint(Color(0xffa8abb1)))
                                        }
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                        Text(
                                            text = "SCHEDULE",
                                            color = Color(0xffa8abb1),
                                            textAlign = TextAlign.Center,
                                            lineHeight = 1.5.em,
                                            style = TextStyle(
                                                                                        fontSize = 10.sp,
                                                                                        fontWeight = FontWeight.Bold,
                                                                                        letterSpacing = (-0.5).sp),
                                            modifier = Modifier
                                                                                        .requiredWidth(width = 50.dp)
                                                                                        .requiredHeight(height = 15.dp)
                                                                                        .wrapContentHeight(align = Alignment.CenterVertically))
                                        }
                                    }
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                                                        .clip(shape = RoundedCornerShape(8.dp))
                                                                        .background(color = Color(0xff0f1418))
                                                                        .padding(start = 33.36000061035156.dp,
                                                                                                            end = 33.380001068115234.dp,
                                                                                                            top = 16.dp,
                                                                                                            bottom = 16.dp)
                                    ) {
                                    Column(
                                        modifier = Modifier
                                                                                .padding(bottom = 8.dp)
                                        ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.container),
                                            contentDescription = "Container",
                                            colorFilter = ColorFilter.tint(Color(0xffa8abb1)))
                                        }
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                        Text(
                                            text = "MONITOR",
                                            color = Color(0xffa8abb1),
                                            textAlign = TextAlign.Center,
                                            lineHeight = 1.5.em,
                                            style = TextStyle(
                                                                                        fontSize = 10.sp,
                                                                                        fontWeight = FontWeight.Bold,
                                                                                        letterSpacing = (-0.5).sp),
                                            modifier = Modifier
                                                                                        .requiredWidth(width = 45.dp)
                                                                                        .requiredHeight(height = 15.dp)
                                                                                        .wrapContentHeight(align = Alignment.CenterVertically))
                                        }
                                    }
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                                                        .clip(shape = RoundedCornerShape(8.dp))
                                                                        .background(color = Color(0xff0f1418))
                                                                        .padding(start = 36.720001220703125.dp,
                                                                                                            end = 36.72999954223633.dp,
                                                                                                            top = 16.dp,
                                                                                                            bottom = 16.dp)
                                    ) {
                                    Column(
                                        modifier = Modifier
                                                                                .padding(bottom = 8.dp)
                                        ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.container),
                                            contentDescription = "Container",
                                            colorFilter = ColorFilter.tint(Color(0xffa8abb1)))
                                        }
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                        Text(
                                            text = "EXPORT",
                                            color = Color(0xffa8abb1),
                                            textAlign = TextAlign.Center,
                                            lineHeight = 1.5.em,
                                            style = TextStyle(
                                                                                        fontSize = 10.sp,
                                                                                        fontWeight = FontWeight.Bold,
                                                                                        letterSpacing = (-0.5).sp),
                                            modifier = Modifier
                                                                                        .requiredWidth(width = 38.dp)
                                                                                        .requiredHeight(height = 15.dp)
                                                                                        .wrapContentHeight(align = Alignment.CenterVertically))
                                        }
                                    }
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                                                        .clip(shape = RoundedCornerShape(8.dp))
                                                                        .background(color = Color(0xff0f1418))
                                                                        .padding(start = 23.770000457763672.dp,
                                                                                                            end = 23.760000228881836.dp,
                                                                                                            top = 16.dp,
                                                                                                            bottom = 16.dp)
                                    ) {
                                    Column(
                                        modifier = Modifier
                                                                                .padding(bottom = 8.dp)
                                        ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.container),
                                            contentDescription = "Container",
                                            colorFilter = ColorFilter.tint(Color(0xffa8abb1)))
                                        }
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                        Text(
                                            text = "VENDOR MAC",
                                            color = Color(0xffa8abb1),
                                            textAlign = TextAlign.Center,
                                            lineHeight = 1.5.em,
                                            style = TextStyle(
                                                                                        fontSize = 10.sp,
                                                                                        fontWeight = FontWeight.Bold,
                                                                                        letterSpacing = (-0.5).sp),
                                            modifier = Modifier
                                                                                        .requiredWidth(width = 64.dp)
                                                                                        .requiredHeight(height = 15.dp)
                                                                                        .wrapContentHeight(align = Alignment.CenterVertically))
                                        }
                                    }
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                                                        .clip(shape = RoundedCornerShape(8.dp))
                                                                        .background(color = Color(0xff0f1418))
                                                                        .padding(horizontal = 33.029998779296875.dp,
                                                                                                            vertical = 16.dp)
                                    ) {
                                    Column(
                                        modifier = Modifier
                                                                                .padding(bottom = 8.dp)
                                        ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.container),
                                            contentDescription = "Container",
                                            colorFilter = ColorFilter.tint(Color(0xffa8abb1)))
                                        }
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                        Text(
                                            text = "SSID MAC",
                                            color = Color(0xffa8abb1),
                                            textAlign = TextAlign.Center,
                                            lineHeight = 1.5.em,
                                            style = TextStyle(
                                                                                        fontSize = 10.sp,
                                                                                        fontWeight = FontWeight.Bold,
                                                                                        letterSpacing = (-0.5).sp),
                                            modifier = Modifier
                                                                                        .requiredWidth(width = 45.dp)
                                                                                        .requiredHeight(height = 15.dp)
                                                                                        .wrapContentHeight(align = Alignment.CenterVertically))
                                        }
                                    }
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                                                        .clip(shape = RoundedCornerShape(8.dp))
                                                                        .background(color = Color(0xff0f1418))
                                                                        .padding(horizontal = 31.920000076293945.dp,
                                                                                                            vertical = 16.dp)
                                    ) {
                                    Column(
                                        modifier = Modifier
                                                                                .padding(bottom = 8.dp)
                                        ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.container),
                                            contentDescription = "Container",
                                            colorFilter = ColorFilter.tint(Color(0xffa8abb1)))
                                        }
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                        Text(
                                            text = "NET SCAN",
                                            color = Color(0xffa8abb1),
                                            textAlign = TextAlign.Center,
                                            lineHeight = 1.5.em,
                                            style = TextStyle(
                                                                                        fontSize = 10.sp,
                                                                                        fontWeight = FontWeight.Bold,
                                                                                        letterSpacing = (-0.5).sp),
                                            modifier = Modifier
                                                                                        .requiredWidth(width = 48.dp)
                                                                                        .requiredHeight(height = 15.dp)
                                                                                        .wrapContentHeight(align = Alignment.CenterVertically))
                                        }
                                    }
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(6.64.dp, Alignment.Start),
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                                                        .fillMaxHeight()
                                                                        .requiredWidth(width = 111.dp)
                                                                        .clip(shape = RoundedCornerShape(8.dp))
                                                                        .background(color = Color(0xff0f1418))
                                                                        .padding(all = 16.dp)
                                    ) {
                                    Column() {
                                        Text(
                                            text = "BOOT MAC",
                                            color = Color(0xffa8abb1),
                                            lineHeight = 1.em,
                                            style = TextStyle(
                                                                                        fontSize = 8.sp,
                                                                                        letterSpacing = (-0.4).sp),
                                            modifier = Modifier
                                                                                        .requiredWidth(width = 41.dp)
                                                                                        .requiredHeight(height = 8.dp)
                                                                                        .wrapContentHeight(align = Alignment.CenterVertically))
                                        }
                                    Column(
                                        modifier = Modifier
                                                                                .requiredWidth(width = 32.dp)
                                                                                .requiredHeight(height = 16.dp)
                                                                                .clip(shape = RoundedCornerShape(12.dp))
                                                                                .background(color = Color(0xff21262c))
                                                                                .padding(start = 18.dp,
                                                                                                                        end = 2.dp,
                                                                                                                        top = 2.dp,
                                                                                                                        bottom = 2.dp)
                                        ) {
                                        Box(
                                            modifier = Modifier
                                                                                        .requiredSize(size = 12.dp)
                                                                                        .clip(shape = RoundedCornerShape(12.dp))
                                                                                        .background(color = Color(0xff69daff)))
                                        }
                                    }
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(3.77.dp, Alignment.Start),
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                                                        .fillMaxHeight()
                                                                        .requiredWidth(width = 111.dp)
                                                                        .clip(shape = RoundedCornerShape(8.dp))
                                                                        .background(color = Color(0xff0f1418))
                                                                        .padding(all = 16.dp)
                                    ) {
                                    Column() {
                                        Text(
                                            text = "AUTO BKUP",
                                            color = Color(0xffa8abb1),
                                            lineHeight = 1.em,
                                            style = TextStyle(
                                                                                        fontSize = 8.sp,
                                                                                        letterSpacing = (-0.4).sp),
                                            modifier = Modifier
                                                                                        .requiredWidth(width = 44.dp)
                                                                                        .requiredHeight(height = 8.dp)
                                                                                        .wrapContentHeight(align = Alignment.CenterVertically))
                                        }
                                    Column(
                                        modifier = Modifier
                                                                                .requiredWidth(width = 32.dp)
                                                                                .requiredHeight(height = 16.dp)
                                                                                .clip(shape = RoundedCornerShape(12.dp))
                                                                                .background(color = Color(0xff21262c))
                                                                                .padding(all = 2.dp)
                                        ) {
                                        Box(
                                            modifier = Modifier
                                                                                        .requiredSize(size = 12.dp)
                                                                                        .clip(shape = RoundedCornerShape(12.dp))
                                                                                        .background(color = Color(0xff72767b)))
                                        }
                                    }
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                                                        .clip(shape = RoundedCornerShape(8.dp))
                                                                        .background(color = Color(0xff0f1418))
                                                                        .padding(start = 30.149999618530273.dp,
                                                                                                            end = 30.15999984741211.dp,
                                                                                                            top = 16.dp,
                                                                                                            bottom = 16.dp)
                                    ) {
                                    Column(
                                        modifier = Modifier
                                                                                .padding(bottom = 8.dp)
                                        ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.container),
                                            contentDescription = "Container",
                                            colorFilter = ColorFilter.tint(Color(0xffa8abb1)))
                                        }
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                        Text(
                                            text = "COPY MAC",
                                            color = Color(0xffa8abb1),
                                            textAlign = TextAlign.Center,
                                            lineHeight = 1.5.em,
                                            style = TextStyle(
                                                                                        fontSize = 10.sp,
                                                                                        fontWeight = FontWeight.Bold,
                                                                                        letterSpacing = (-0.5).sp),
                                            modifier = Modifier
                                                                                        .requiredWidth(width = 51.dp)
                                                                                        .requiredHeight(height = 15.dp)
                                                                                        .wrapContentHeight(align = Alignment.CenterVertically))
                                        }
                                    }
                                }
                            }
                        }
                    item {
                        Column(
                            modifier = Modifier
                                                        .fillMaxWidth()
                            ) {
                            Column(
                                modifier = Modifier
                                                                .fillMaxWidth()
                                                                .padding(bottom = 12.dp)
                                ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(149.5.dp, Alignment.Start),
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                                                        .fillMaxWidth()
                                                                        .padding(horizontal = 4.dp)
                                    ) {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                                        verticalAlignment = Alignment.CenterVertically
                                        ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.container),
                                            contentDescription = "Container",
                                            colorFilter = ColorFilter.tint(Color(0xffa8abb1)))
                                        Column() {
                                            Text(
                                                text = "SYSTEM CONSOLE",
                                                color = Color(0xffa8abb1),
                                                lineHeight = 1.33.em,
                                                style = TextStyle(
                                                                                                fontSize = 12.sp,
                                                                                                fontWeight = FontWeight.Bold,
                                                                                                letterSpacing = 1.2.sp),
                                                modifier = Modifier
                                                                                                .requiredWidth(width = 118.dp)
                                                                                                .requiredHeight(height = 16.dp)
                                                                                                .wrapContentHeight(align = Alignment.CenterVertically))
                                            }
                                        }
                                    Column(
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                        Text(
                                            text = "CLEAR LOGS",
                                            color = Color(0xff69daff),
                                            textAlign = TextAlign.Center,
                                            lineHeight = 1.5.em,
                                            style = TextStyle(
                                                                                        fontSize = 10.sp,
                                                                                        fontWeight = FontWeight.Bold),
                                            modifier = Modifier
                                                                                        .requiredWidth(width = 63.dp)
                                                                                        .requiredHeight(height = 15.dp)
                                                                                        .wrapContentHeight(align = Alignment.CenterVertically))
                                        }
                                    }
                                }
                            Column(
                                modifier = Modifier
                                                                .fillMaxWidth()
                                                                .clip(shape = RoundedCornerShape(8.dp))
                                                                .background(color = Color.Black)
                                                                .border(border = BorderStroke(1.dp, Color(0xff44484d).copy(alpha = 0.1f)),
                                                                                                shape = RoundedCornerShape(8.dp))
                                                                .padding(start = 20.dp,
                                                                                                end = 20.dp,
                                                                                                top = 19.dp,
                                                                                                bottom = 20.dp)
                                ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
                                    modifier = Modifier
                                                                        .fillMaxWidth()
                                    ) {
                                    Column(
                                        modifier = Modifier
                                                                                .fillMaxHeight()
                                        ) {
                                        Text(
                                            text = "12:05:01",
                                            color = Color(0xff44484d),
                                            lineHeight = 1.63.em,
                                            style = TextStyle(
                                                                                        fontSize = 14.sp),
                                            modifier = Modifier
                                                                                        .requiredWidth(width = 54.dp)
                                                                                        .requiredHeight(height = 23.dp)
                                                                                        .wrapContentHeight(align = Alignment.CenterVertically))
                                        }
                                    Column(
                                        modifier = Modifier
                                                                                .fillMaxHeight()
                                                                                .padding(end = 51.810001373291016.dp)
                                        ) {
                                        Text(
                                            text = "# Initializing OBSIDIAN kernel\nhooks...",
                                            color = Color(0xff8eff71),
                                            lineHeight = 1.63.em,
                                            style = TextStyle(
                                                                                        fontSize = 14.sp),
                                            modifier = Modifier
                                                                                        .requiredWidth(width = 194.dp)
                                                                                        .requiredHeight(height = 46.dp)
                                                                                        .wrapContentHeight(align = Alignment.CenterVertically))
                                        }
                                    }
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
                                    modifier = Modifier
                                                                        .fillMaxWidth()
                                    ) {
                                    Column(
                                        modifier = Modifier
                                                                                .fillMaxHeight()
                                        ) {
                                        Text(
                                            text = "12:05:02",
                                            color = Color(0xff44484d),
                                            lineHeight = 1.63.em,
                                            style = TextStyle(
                                                                                        fontSize = 14.sp),
                                            modifier = Modifier
                                                                                        .requiredWidth(width = 57.dp)
                                                                                        .requiredHeight(height = 23.dp)
                                                                                        .wrapContentHeight(align = Alignment.CenterVertically))
                                        }
                                    Column(
                                        modifier = Modifier
                                                                                .fillMaxHeight()
                                                                                .padding(end = 25.549999237060547.dp)
                                        ) {
                                        Text(
                                            text = "@ SYSTEM: Root access verified\n(UID: 0).",
                                            color = Color(0xffebeef4),
                                            lineHeight = 1.63.em,
                                            style = TextStyle(
                                                                                        fontSize = 14.sp),
                                            modifier = Modifier
                                                                                        .requiredWidth(width = 218.dp)
                                                                                        .requiredHeight(height = 46.dp)
                                                                                        .wrapContentHeight(align = Alignment.CenterVertically))
                                        }
                                    }
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
                                    modifier = Modifier
                                                                        .fillMaxWidth()
                                    ) {
                                    Column(
                                        modifier = Modifier
                                                                                .fillMaxHeight()
                                        ) {
                                        Text(
                                            text = "12:05:04",
                                            color = Color(0xff44484d),
                                            lineHeight = 1.63.em,
                                            style = TextStyle(
                                                                                        fontSize = 14.sp),
                                            modifier = Modifier
                                                                                        .requiredWidth(width = 57.dp)
                                                                                        .requiredHeight(height = 23.dp)
                                                                                        .wrapContentHeight(align = Alignment.CenterVertically))
                                        }
                                    Column(
                                        modifier = Modifier
                                                                                .fillMaxHeight()
                                                                                .padding(end = 36.5.dp)
                                        ) {
                                        Text(
                                            text = "@ NET: Fetching interface eth0\ndescriptor...",
                                            color = Color(0xffebeef4),
                                            lineHeight = 1.63.em,
                                            style = TextStyle(
                                                                                        fontSize = 14.sp),
                                            modifier = Modifier
                                                                                        .requiredWidth(width = 206.dp)
                                                                                        .requiredHeight(height = 46.dp)
                                                                                        .wrapContentHeight(align = Alignment.CenterVertically))
                                        }
                                    }
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
                                    modifier = Modifier
                                                                        .fillMaxWidth()
                                    ) {
                                    Column(
                                        modifier = Modifier
                                                                                .fillMaxHeight()
                                        ) {
                                        Text(
                                            text = "12:05:05",
                                            color = Color(0xff44484d),
                                            lineHeight = 1.63.em,
                                            style = TextStyle(
                                                                                        fontSize = 14.sp),
                                            modifier = Modifier
                                                                                        .requiredWidth(width = 57.dp)
                                                                                        .requiredHeight(height = 23.dp)
                                                                                        .wrapContentHeight(align = Alignment.CenterVertically))
                                        }
                                    Column(
                                        modifier = Modifier
                                                                                .fillMaxHeight()
                                        ) {
                                        Text(
                                            text = "> IP ADDR: 192.168.1.104",
                                            color = Color(0xff00c0ea),
                                            lineHeight = 1.63.em,
                                            style = TextStyle(
                                                                                        fontSize = 14.sp),
                                            modifier = Modifier
                                                                                        .requiredWidth(width = 160.dp)
                                                                                        .requiredHeight(height = 23.dp)
                                                                                        .wrapContentHeight(align = Alignment.CenterVertically))
                                        }
                                    }
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
                                    modifier = Modifier
                                                                        .fillMaxWidth()
                                    ) {
                                    Column(
                                        modifier = Modifier
                                                                                .fillMaxHeight()
                                        ) {
                                        Text(
                                            text = "12:05:05",
                                            color = Color(0xff44484d),
                                            lineHeight = 1.63.em,
                                            style = TextStyle(
                                                                                        fontSize = 14.sp),
                                            modifier = Modifier
                                                                                        .requiredWidth(width = 57.dp)
                                                                                        .requiredHeight(height = 23.dp)
                                                                                        .wrapContentHeight(align = Alignment.CenterVertically))
                                        }
                                    Column(
                                        modifier = Modifier
                                                                                .fillMaxHeight()
                                        ) {
                                        Text(
                                            text = "> GATEWAY: 192.168.1.1",
                                            color = Color(0xff00c0ea),
                                            lineHeight = 1.63.em,
                                            style = TextStyle(
                                                                                        fontSize = 14.sp),
                                            modifier = Modifier
                                                                                        .requiredWidth(width = 153.dp)
                                                                                        .requiredHeight(height = 23.dp)
                                                                                        .wrapContentHeight(align = Alignment.CenterVertically))
                                        }
                                    }
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
                                    modifier = Modifier
                                                                        .fillMaxWidth()
                                    ) {
                                    Column(
                                        modifier = Modifier
                                                                                .fillMaxHeight()
                                        ) {
                                        Text(
                                            text = "12:05:08",
                                            color = Color(0xff44484d),
                                            lineHeight = 1.63.em,
                                            style = TextStyle(
                                                                                        fontSize = 14.sp),
                                            modifier = Modifier
                                                                                        .requiredWidth(width = 57.dp)
                                                                                        .requiredHeight(height = 23.dp)
                                                                                        .wrapContentHeight(align = Alignment.CenterVertically))
                                        }
                                    Column(
                                        modifier = Modifier
                                                                                .fillMaxHeight()
                                                                                .padding(end = 33.27000045776367.dp)
                                        ) {
                                        Text(
                                            text = "@ NET: Interface eth0 ready for\nMAC override.",
                                            color = Color(0xffebeef4),
                                            lineHeight = 1.63.em,
                                            style = TextStyle(
                                                                                        fontSize = 14.sp),
                                            modifier = Modifier
                                                                                        .requiredWidth(width = 210.dp)
                                                                                        .requiredHeight(height = 46.dp)
                                                                                        .wrapContentHeight(align = Alignment.CenterVertically))
                                        }
                                    }
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
                                    modifier = Modifier
                                                                        .fillMaxWidth()
                                    ) {
                                    Column(
                                        modifier = Modifier
                                                                                .fillMaxHeight()
                                        ) {
                                        Text(
                                            text = "12:06:12",
                                            color = Color(0xff44484d),
                                            lineHeight = 1.63.em,
                                            style = TextStyle(
                                                                                        fontSize = 14.sp),
                                            modifier = Modifier
                                                                                        .requiredWidth(width = 54.dp)
                                                                                        .requiredHeight(height = 23.dp)
                                                                                        .wrapContentHeight(align = Alignment.CenterVertically))
                                        }
                                    Column(
                                        modifier = Modifier
                                                                                .fillMaxHeight()
                                        ) {
                                        Text(
                                            text = "// Standing by for user command...",
                                            color = Color(0xffa8abb1),
                                            lineHeight = 1.63.em,
                                            style = TextStyle(
                                                                                        fontSize = 14.sp),
                                            modifier = Modifier
                                                                                        .requiredWidth(width = 230.dp)
                                                                                        .requiredHeight(height = 23.dp)
                                                                                        .wrapContentHeight(align = Alignment.CenterVertically))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            Row(
                horizontalArrangement = Arrangement.spacedBy(27.3.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                                .align(alignment = Alignment.BottomStart)
                                .offset(x = 0.dp,
                                                y = 0.dp)
                                .requiredWidth(width = 390.dp)
                                .clip(shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                                .background(color = Color(0xff0f1418).copy(alpha = 0.7f))
                                .padding(start = 29.6299991607666.dp,
                                                end = 29.649999618530273.dp,
                                                top = 12.dp,
                                                bottom = 12.dp)
                                .shadow(elevation = 40.dp,
                                                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                                        .clip(shape = RoundedCornerShape(8.dp))
                                        .background(color = Color(0xff21262c))
                                        .padding(all = 12.dp)
                                        .shadow(elevation = 12.dp,
                                                            shape = RoundedCornerShape(8.dp))
                    ) {
                    Image(
                        painter = painterResource(id = R.drawable.container),
                        contentDescription = "Container",
                        colorFilter = ColorFilter.tint(Color(0xff00d1ff)))
                    Column(
                        modifier = Modifier
                                                .padding(top = 4.dp)
                        ) {
                        Text(
                            text = "STATUS",
                            color = Color(0xff00d1ff),
                            lineHeight = 1.5.em,
                            style = TextStyle(
                                                        fontSize = 10.sp,
                                                        fontWeight = FontWeight.Bold),
                            modifier = Modifier
                                                        .requiredWidth(width = 39.dp)
                                                        .requiredHeight(height = 15.dp)
                                                        .wrapContentHeight(align = Alignment.CenterVertically))
                        }
                    }
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                                        .padding(all = 12.dp)
                    ) {
                    Image(
                        painter = painterResource(id = R.drawable.container),
                        contentDescription = "Container",
                        colorFilter = ColorFilter.tint(Color(0xffa8abb1)))
                    Column(
                        modifier = Modifier
                                                .padding(top = 4.dp)
                        ) {
                        Text(
                            text = "ADAPTER",
                            color = Color(0xffa8abb1),
                            lineHeight = 1.5.em,
                            style = TextStyle(
                                                        fontSize = 10.sp,
                                                        fontWeight = FontWeight.Bold),
                            modifier = Modifier
                                                        .requiredWidth(width = 48.dp)
                                                        .requiredHeight(height = 15.dp)
                                                        .wrapContentHeight(align = Alignment.CenterVertically))
                        }
                    }
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                                        .padding(all = 12.dp)
                    ) {
                    Image(
                        painter = painterResource(id = R.drawable.container),
                        contentDescription = "Container",
                        colorFilter = ColorFilter.tint(Color(0xffa8abb1)))
                    Column(
                        modifier = Modifier
                                                .padding(top = 4.dp)
                        ) {
                        Text(
                            text = "LOGS",
                            color = Color(0xffa8abb1),
                            lineHeight = 1.5.em,
                            style = TextStyle(
                                                        fontSize = 10.sp,
                                                        fontWeight = FontWeight.Bold),
                            modifier = Modifier
                                                        .requiredWidth(width = 27.dp)
                                                        .requiredHeight(height = 15.dp)
                                                        .wrapContentHeight(align = Alignment.CenterVertically))
                        }
                    }
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                                        .padding(all = 12.dp)
                    ) {
                    Image(
                        painter = painterResource(id = R.drawable.container),
                        contentDescription = "Container",
                        colorFilter = ColorFilter.tint(Color(0xffa8abb1)))
                    Column(
                        modifier = Modifier
                                                .padding(top = 4.dp)
                        ) {
                        Text(
                            text = "CONFIG",
                            color = Color(0xffa8abb1),
                            lineHeight = 1.5.em,
                            style = TextStyle(
                                                        fontSize = 10.sp,
                                                        fontWeight = FontWeight.Bold),
                            modifier = Modifier
                                                        .requiredWidth(width = 39.dp)
                                                        .requiredHeight(height = 15.dp)
                                                        .wrapContentHeight(align = Alignment.CenterVertically))
                        }
                    }
                }
            }
        }
 }

@Preview(widthDp = 390, heightDp = 1865)
@Composable
private fun BodyPreview() {
    Body(Modifier)
 }