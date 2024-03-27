const express = require('express');
const app = express();
const port = 3000;

const bodyParser = require('body-parser');
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true}));

app.listen(port,  ()=>{
    console.log('Server run 3000');
});

const COMMON = require('./COMMON');
const uri = COMMON.uri;
const mongoose = require('mongoose');
const titleModel = require('./titleModel');
const apiMobile = require('./api');
app.use('/api', apiMobile);

app.get('/', async (req, res)=>{
    await mongoose.connect(uri);

    let titles = await titleModel.find();
    console.log(titles);
    res.send(titles);
})
app.post('/add', async (req, res)=>{
    await mongoose.connect(uri);
    let title = req.body;
    let kq = await titleModel.create(title);
    console.log(kq);
    let titles = await titleModel.find();
    res.send(titles);
})
app.delete('/xoa/:id', async (req, res)=>{
    try{
        await mongoose.connect(uri);
        let id = req.params.id;
        console.log(id);

        const kq = await titleModel.deleteOne({_id: id});
        if (kq) {
            res.send('Xoa thanh cong');
        } else{
            res.send('Xoa that bai');
    }
    res.redirect('../');
    } catch(error){
        res.send('loi');
    }
    
});
app.put('/update/:id', async (req, res)=>{
    try{
        const id = req.params.id;
        const data = req.body;

        await mongoose.connect(uri);
        const kq = await titleModel.findByIdAndUpdate(id, data);
        if (kq) {
            res.send('Update thanh cong');
            console.log('Update thanh cong id:' +id);
        } else{
            res.send('Update that bai');
            console.log('Update that bai id:' +id);
        }
    } catch(error){
        res.send('Loi update');
    }
})