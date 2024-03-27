const express = require('express');
const router = express.Router();
module.exports = router;
router.get('/', (req, res)=>{
    res.send('vao api');
})
const COMMON = require('./COMMON');
const uri = COMMON.uri;
const mongoose = require('mongoose');
const titleModel = require('./titleModel');
router.get('/lab5', async (req, res)=>{
    await mongoose.connect(uri);

    let titles = await titleModel.find();
    console.log(titles);
    res.send(titles);
})
router.post('/add', async (req, res)=>{
    try {
        await mongoose.connect(uri);
        
        let title = req.body;
        let kq = await titleModel.create(title);
        console.log(kq);
        if (kq) {
            console.log('Thêm thành công');
            let titles = await titleModel.find();
            console.log(titles);
            return res.send(titles);
        } else {
            console.log('Thêm that bai');
            return res.send('Thêm that bai');
        }
    } catch (error) {
        console.log('loi');
        return res.send('loi');
    }
});
router.delete('/xoa/:id', async (req, res) => {
    try {
        await mongoose.connect(uri);
        let id = req.params.id;
        console.log(id);

        const kq = await titleModel.deleteOne({ _id: id });
        if (kq) {
            console.log('Xoa thanh cong');
            let titles = await titleModel.find();
            console.log(titles);
            return res.send(titles);
        } else {
            console.log('Xoa that bai');
            return res.send('Xoa that bai');
        }
    } catch (error) {
        console.log('loi');
        return res.send('loi');
    }
});
router.put('/update/:id', async (req, res) => {
    try {
        const id = req.params.id;
        const data = req.body;

        await mongoose.connect(uri);
        const kq = await titleModel.findByIdAndUpdate(id, data);
        if (kq) {
            console.log('Update thanh cong id: ' + id);
            let titles = await titleModel.find();
            console.log(titles);
            return res.send(titles);
        } else {
            console.log('Update that bai id: ' + id);
            return res.send('Update that bai');
        }
    } catch (error) {
        return res.send('Loi update');
    }
});
router.get('/timkiem/:name', async (req, res) => {
    try {
        const name = req.query.name;
        const data = req.body;
        
        await module.connect(uri);
        const kq = await titleModel.create(name, data);
        if(kq){
            console.log('tìm thành công', name);
            let titles = await titleModel.find();
            console.log(titles);
            return res.send(titles);
        } else{
            console.log('tìm that bai');
        }
    } catch (error) {
        console.log(error);
    }
});